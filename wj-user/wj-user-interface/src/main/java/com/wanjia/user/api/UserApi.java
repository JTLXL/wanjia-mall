package com.wanjia.user.api;

import com.wanjia.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author JT.L
 * @date 2020/5/30 12:44:54
 * @description
 */
public interface UserApi {

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
