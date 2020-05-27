package com.jtl.page.service;

import com.jtl.page.client.BrandClient;
import com.jtl.page.client.CategoryClient;
import com.jtl.page.client.GoodsClient;
import com.jtl.page.client.SpecificationClient;
import com.wanjia.item.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JT.L
 * @date 2020/4/30 16:52:46
 * @description
 */
@Slf4j
@Service
public class PageService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specClient;

    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long spuId) {
        Map<String, Object> model = new HashMap<>();
        // 查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        // 查询skus
        List<Sku> skus = spu.getSkus();
        // 查询详情
        SpuDetail detail = spu.getSpuDetail();
        // 查询brand
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        // 查询商品分类
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        //查询规格参数
        List<SpecGroup> specs = specClient.queryGroupByCid(spu.getCid3());

        // 防止重复数据
        spu.setSkus(null);
        spu.setSpuDetail(null);
        /*Map<String,String> spu2 = new HashMap<>();
        spu2.put("title",spu.getTitle());
        spu2.put("subTitle",spu.getSubTitle());*/
        model.put("spu", spu);
        model.put("skus", skus);
        model.put("detail", detail);
        model.put("brand", brand);
        model.put("categories", categories);
        model.put("specs", specs);
        return model;
    }

    public void createHtml(Long spuId) {
        // 上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));

        // 输出流
        File dest = new File("D:\\project\\wanjiamall\\upload", spuId + ".html");

        // 针对mq做的一些优化操作
        // 收到[item.update]消息时
        // 把之前存在的静态页先删除
        if (dest.exists()) {
            dest.delete();
        }

        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            // 生成HTML
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            log.error("[静态页服务] 生成静态页异常!", e);
        }
    }

    /**
     * 监听到[item.delete]消息时，
     * 删除对应静态页
     *
     * @param spuId
     */
    public void deleteHtml(Long spuId) {
        // 提高代码健壮性
        String separator = File.separator;
        String path = "D:" + separator + "project" + separator + "wanjiamall" + separator + "upload";
        // File dest0 = new File("D:\\project\\wanjiamall\\upload", spuId + ".html");
        File dest = new File(path, spuId + ".html");
        if (dest.exists()) {
            dest.delete();
        }
    }
}
