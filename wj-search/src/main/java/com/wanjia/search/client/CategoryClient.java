package com.wanjia.search.client;

import com.wanjia.item.api.CategoryApi;
import com.wanjia.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/4/10 20:43:47
 * @description
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
