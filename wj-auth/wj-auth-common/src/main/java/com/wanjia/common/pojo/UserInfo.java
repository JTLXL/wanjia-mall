package com.wanjia.common.pojo;

import lombok.Data;

/**
 * @author JT.L
 * @date 2020/5/29 12:39:21
 * @description 载荷对象
 */
@Data
public class UserInfo {

    private Long id;

    private String username;

    public UserInfo() {
    }

    public UserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
