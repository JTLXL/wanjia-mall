package com.wanjia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author JT.L
 * @date 2020/3/14 15:02:40
 * @description
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400, "价格不能为空！");

    /**
     * 状态吗
     */
    private int code;
    /**
     * 提示消息
     */
    private String msg;

    // 这是私有构造函数类的创建对象方式
    // private static final ExceptionEnum ff = new ExceptionEnum(200,"");

}
