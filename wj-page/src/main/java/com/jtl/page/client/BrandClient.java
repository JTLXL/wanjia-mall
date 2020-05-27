package com.jtl.page.client;

import com.wanjia.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author JT.L
 * @date 2020/4/12 17:39:40
 * @description
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
