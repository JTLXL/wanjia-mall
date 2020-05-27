package com.jtl.page.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JT.L
 * @date 2020/4/26 21:16:00
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String name;
    int age;
    /**
     * 对象类型属性
     */
    User friend;
}
