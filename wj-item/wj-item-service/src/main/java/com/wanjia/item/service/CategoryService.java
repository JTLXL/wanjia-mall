package com.wanjia.item.service;

import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.item.mapper.CategoryMapper;
import com.wanjia.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/3/15 17:34:08
 * @description
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid) {
        // 查询条件，mapper会把对象中的非空属性作为查询条件
        Category t = new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);
        // 判断结果
        if (CollectionUtils.isEmpty(list)) {
            throw new WjException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
}
