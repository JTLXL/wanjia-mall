package com.wanjia.search.repository;

import com.wanjia.common.vo.PageResult;
import com.wanjia.item.pojo.Spu;
import com.wanjia.search.client.GoodsClient;
import com.wanjia.search.pojo.Goods;
import com.wanjia.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    /**
     * 创建索引库
     * 只需要执行一次，所以写在了test里
     * 直接在Kibana去创建也是可以的
     */
    @Test
    public void TestCreateIndex() {
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }

    /**
     * 往索引库中添加goods数据
     */
    @Test
    public void loadData() {
        Integer page = 1;
        Integer rows = 100;
        int size = 0;
        do {
            PageResult<Spu> result = goodsClient.querySpuByPage(page, rows, true, null);
            List<Spu> spuList = result.getItems();
            if (CollectionUtils.isEmpty(spuList)) {
                break;
            }
            List<Goods> goodsList = spuList.stream().map(searchService::buildGoods).collect(Collectors.toList());
            goodsRepository.saveAll(goodsList);
            // 翻页
            page++;
            size = spuList.size();
        } while (size == rows);

        // 方式2
        /*PageResult<Spu> result = goodsClient.querySpuByPage(page, rows, true, null);
        Long totalPage = result.getTotalPage();
        do {
            // 与上面的逻辑一样
            page++;
        } while (page <= totalPage);*/

    }

}