package com.wanjia.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.common.utils.JsonUtils;
import com.wanjia.common.vo.PageResult;
import com.wanjia.item.pojo.*;
import com.wanjia.search.client.BrandClient;
import com.wanjia.search.client.CategoryClient;
import com.wanjia.search.client.GoodsClient;
import com.wanjia.search.client.SpecificationClient;
import com.wanjia.search.pojo.Goods;
import com.wanjia.search.pojo.SearchRequest;
import com.wanjia.search.pojo.SearchResult;
import com.wanjia.search.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author JT.L
 * @date 2020/4/12 18:51:11
 * @description
 */
@Slf4j
@Service
public class SearchService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecificationClient specClient;

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 根据spu构建Goods对象
     *
     * @param spu
     * @return
     */
    public Goods buildGoods(Spu spu) {
        Long spuId = spu.getId();
        // query categories
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)) {
            throw new WjException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        // query brand
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if (brand == null) {
            throw new WjException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        // the field for searching
        String all = spu.getTitle() + StringUtils.join(names, " ") + brand.getName();
        // query the SKU
        List<Sku> skuList = goodsClient.querySkuBySpuId(spuId);
        if (CollectionUtils.isEmpty(skuList)) {
            throw new WjException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 处理SKU
        List<Map<String, Object>> skus = new ArrayList<>();
        // price set
        Set<Long> priceSet = new HashSet<>();
        skuList.forEach(s -> {
            Map map = new HashMap();
            map.put("id", s.getId());
            map.put("title", s.getTitle());
            map.put("price", s.getPrice());
            // map.put("image", s.getImages().split(",")[0]);
            map.put("image", StringUtils.substringBefore(s.getImages(), ","));
            skus.add(map);
            // 处理price
            priceSet.add(s.getPrice());
        });

        // price set
        // Set<Long> priceSet = skuList.stream().map(Sku::getPrice).collect(Collectors.toSet());

        // 查询规格参数
        List<SpecParam> params = specClient.queryParamList(null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(params)) {
            throw new WjException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        // 查询商品详情
        SpuDetail spuDetail = goodsClient.queryDetailById(spuId);
        // 获取通用规格参数
        Map<Long, String> genericSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
        // 获取特有规格参数
        Map<Long, List<String>> specialSpec = JsonUtils
                .nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
                });
        // 规格参数，key是规格参数的name，值是规格阐述的值
        Map<String, Object> specs = new HashMap<>();
        params.forEach(param -> {
            // spec name
            String key = param.getName();
            Object value = "";
            if (param.getGeneric()) {
                value = genericSpec.get(param.getId());
                // 判断是否是数值类型
                if (param.getNumeric()) {
                    // 处理成段
                    value = chooseSegment(value.toString(), param);
                }
            } else {
                value = specialSpec.get(param.getId());
            }
            specs.put(key, value);
        });

        // 构建goods对象
        Goods goods = new Goods();
        goods.setId(spuId);
        goods.setAll(all);
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setPrice(priceSet);
        goods.setSkus(JsonUtils.serialize(skus));
        goods.setSpecs(specs);
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> search(SearchRequest request) {
        //
        Integer page = request.getPage() - 1;
        Integer size = request.getSize();
        // 创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));
        // 分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 过滤(搜索条件)
        //QueryBuilder basicQuery = QueryBuilders.matchQuery("all", request.getKey());
        QueryBuilder basicQuery = buildBasicQuery(request);
        queryBuilder.withQuery(basicQuery);

        // 聚合分类和品牌
        String categoryAggName = "category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));

        String brandAggName = "brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 查询
        // Page<Goods> result = repository.search(queryBuilder.build());
        // 查聚合需要用template
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);

        // 解析结果
        // 解析分页结果
        long total = result.getTotalElements();
        // 返回是int类型用long接受，因为PageResult的构造器中需要的是long类型的数据
        long totalPages = result.getTotalPages();
        List<Goods> goodsList = result.getContent();
        // 解析聚合结果
        Aggregations aggs = result.getAggregations();
        List<Category> categories = parseCategoryAgg(aggs.get(categoryAggName));
        List<Brand> brands = parseBrandAgg(aggs.get(brandAggName));

        // 规格参数聚合
        List<Map<String, Object>> specs = null;
        if (categories != null && categories.size() == 1) {
            specs = buildSpecificationAgg(categories.get(0).getId(), basicQuery);
        }

        // return new PageResult<>(total, totalPages, goodsList);
        return new SearchResult(total, totalPages, goodsList, categories, brands, specs);
    }

    /**
     * 构建基本查询
     * 查询方式bool查询，目的：将搜索条件与过滤条件分开
     *
     * @param request
     * @return
     */
    private QueryBuilder buildBasicQuery(SearchRequest request) {
        // 创建布尔查询
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()));
        // 过滤条件
        Map<String, String> map = request.getFilter();
        String cid = "cid3", brandId = "brandId";
        map.forEach((k, v) -> {
            if (!cid.equals(k) && !brandId.equals(k)) {
                k = "specs." + k + ".keyword";
            }
            queryBuilder.filter(QueryBuilders.termQuery(k, v));
        });
        return queryBuilder;
    }

    private List<Map<String, Object>> buildSpecificationAgg(Long cid, QueryBuilder basicQuery) {
        List<Map<String, Object>> specs = new ArrayList<>();
        // 查询需要聚合的规格参数
        List<SpecParam> params = specClient.queryParamList(null, cid, true);
        // 聚合
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(basicQuery);
        params.forEach(param -> {
            String name = param.getName();
            queryBuilder.addAggregation(
                    AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        });
        // 获取结果
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        // 解析结果
        Aggregations aggs = result.getAggregations();
        params.forEach(p -> {
            String name = p.getName();
            StringTerms terms = aggs.get(name);
            /*List<String> options = terms.getBuckets()
                    .stream().map(b -> b.getKeyAsString()).collect(Collectors.toList());*/
            List<String> options = terms.getBuckets()
                    .stream().map(StringTerms.Bucket::getKeyAsString).collect(Collectors.toList());
            Map<String, Object> map = new HashMap<>();
            map.put("k", name);
            map.put("options", options);
            specs.add(map);
        });
        return specs;
    }

    private List<Brand> parseBrandAgg(LongTerms terms) {
        // 如果没查到brand下面的代码会抛异常
        // 所以最好try起来，如果真的抛了异常catch一下
        // return null，因为没查到brand证明没有聚合到任何品牌
        try {
            List<Long> ids = terms.getBuckets()
                    .stream().map(b -> b.getKeyAsNumber().longValue())
                    .collect(Collectors.toList());
            return brandClient.queryBrandByIds(ids);
        } catch (Exception e) {
            log.error("[搜索服务]查询品牌异常:", e);
            return null;
        }
    }

    private List<Category> parseCategoryAgg(LongTerms terms) {
        try {
            List<Long> ids = terms.getBuckets()
                    .stream().map(b -> b.getKeyAsNumber().longValue())
                    .collect(Collectors.toList());
            return categoryClient.queryCategoryByIds(ids);
        } catch (Exception e) {
            log.error("[搜索服务]查询分类异常:", e);
            return null;
        }
    }

    /**
     * 监听到[item.insert]和[item.update]消息
     * 从索引库新增/修改商品
     * @param spuId
     */
    public void createOrUpdateIndex(Long spuId) {
        // 查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        // 构建goods
        Goods goods = buildGoods(spu);
        // 存入索引库
        repository.save(goods);
    }

    /**
     * 监听到[item.delete]消息
     * 从索引库删除商品
     * @param spuId
     */
    public void deleteIndex(Long spuId) {
        repository.deleteById(spuId);
    }
}
