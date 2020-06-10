package com.wanjia.cart.pojo;

import lombok.Data;

/**
 * @author JT.L
 * @date 2020/6/8 16:34:29
 * @description
 */
@Data
public class Cart {
    // private Long userId;// 用户id

    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String image;
    /**
     * 加入购物车时的价格
     */
    private Long price;
    /**
     * 购买数量
     */
    private Integer num;
    /**
     * 商品规格参数
     */
    private String ownSpec;
}
