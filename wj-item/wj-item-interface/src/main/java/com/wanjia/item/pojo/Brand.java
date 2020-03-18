package com.wanjia.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author JT.L
 * @date 2020/3/17 16:52:27
 * @description
 */
@Data
@Table(name = "tb_brand")
public class Brand {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    // 品牌名称
    private String name;
    // 品牌图片
    private String image;
    private Character letter;
}
