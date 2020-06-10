package com.wanjia.user.web;

import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.user.pojo.User;
import com.wanjia.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 注册
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        Boolean res = userService.register(user, code);
        if (res == null || !res) {
            // 或者直接在service抛一个相应的WjException异常
            // throw new WjException(ExceptionEnum.INVALID_USER_DATA_TYPE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

}
