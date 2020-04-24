package com.wanjia.item.api;

import com.wanjia.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/4/12 17:34:31
 * @description
 */
public interface BrandApi {
    /**
     * 根据id查询品牌
     *
     * @param id
     * @return
     */
    @GetMapping("brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);

    /**
     * 根据多个id批量查询brand信息
     * @param ids
     * @return
     */
    @GetMapping("brand/list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
