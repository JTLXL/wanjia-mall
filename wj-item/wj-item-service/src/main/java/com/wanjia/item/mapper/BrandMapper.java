package com.wanjia.item.mapper;

import com.wanjia.common.mapper.BaseMapper;
import com.wanjia.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/3/17 16:55:45
 * @description
 */
@Repository
public interface BrandMapper extends BaseMapper<Brand> {
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 根据cid查询品牌
     * @param cid
     * @return
     */
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> queryByCategoryId(@Param("cid") Long cid);
}
