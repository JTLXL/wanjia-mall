package com.wanjia.search.client;

import com.wanjia.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author JT.L
 * @date 2020/4/12 17:38:37
 * @description
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
