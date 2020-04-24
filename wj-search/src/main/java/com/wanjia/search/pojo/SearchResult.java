package com.wanjia.search.pojo;

import com.wanjia.common.vo.PageResult;
import com.wanjia.item.pojo.Brand;
import com.wanjia.item.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author JT.L
 * @date 2020/4/17 21:04:03
 * @description
 */
@Data
@NoArgsConstructor
public class SearchResult extends PageResult<Goods> {

    /**
     * 分类过滤条件（分类待选项）
     */
    private List<Category> categories;
    /**
     * 品牌过滤条件（品牌待选项）
     */
    private List<Brand> brands;

    /**
     * 规格参数过滤条件（key及待选项）
     */
    private List<Map<String, Object>> specs;


    /*public SearchResult(Long total, Long totalPage, List<Goods> items, List<Category> categories, List<Brand> brands) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
    }*/

    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
