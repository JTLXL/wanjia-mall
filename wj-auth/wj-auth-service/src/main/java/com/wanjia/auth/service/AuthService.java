package com.wanjia.auth.service;

import com.wanjia.auth.client.UserClient;
import com.wanjia.auth.config.JwtProperties;
import com.wanjia.common.pojo.UserInfo;
import com.wanjia.common.utils.JwtUtils;
import com.wanjia.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JT.L
 * @date 2020/5/30 10:55:17
 * @description
 */
@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties prop;

    public String accredit(String username, String password) {
        // 根据用户名和密码(远程调用user-service服务)查询
        User user = userClient.queryUser(username, password);
        if (user == null) {
            return null;
        }
        // 生成jwt类型的token
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        try {
            // 没有异常直接返回，否则打印并最后返回null
            return JwtUtils.generateToken(userInfo, prop.getPrivateKey(), prop.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
