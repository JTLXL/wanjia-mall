package com.jtl.page.client;

import com.wanjia.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author JT.L
 * @date 2020/4/10 20:43:47
 * @description
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
