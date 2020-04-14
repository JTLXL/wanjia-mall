package com.wanjia.item.api;

import com.wanjia.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/4/12 17:32:18
 * @description
 */
public interface CategoryApi {
    /**
     * 根据多个cid查询商品分类信息
     * @param ids
     * @return
     */
    @GetMapping("category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
