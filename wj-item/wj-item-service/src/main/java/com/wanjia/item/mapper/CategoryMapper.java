package com.wanjia.item.mapper;

import com.wanjia.item.pojo.Category;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author JT.L
 * @date 2020/3/15 17:31:49
 * @description
 */
@Repository
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {
}
