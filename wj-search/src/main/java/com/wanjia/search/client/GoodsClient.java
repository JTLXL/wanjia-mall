package com.wanjia.search.client;

import com.wanjia.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author JT.L
 * @date 2020/4/10 21:24:00
 * @description
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
