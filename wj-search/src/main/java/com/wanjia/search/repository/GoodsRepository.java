package com.wanjia.search.repository;

import com.wanjia.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author JT.L
 * @date 2020/4/12 17:58:02
 * @description
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
