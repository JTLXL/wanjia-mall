package com.wanjia.user.web;

import com.wanjia.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author JT.L
 * @date 2020/5/26 14:19:23
 * @description
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验数据
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型: 1.用户名; 2.手机；
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone) {
        userService.sendVerifyCode(phone);
        // 成功返回204 .build()代表无返回值
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
