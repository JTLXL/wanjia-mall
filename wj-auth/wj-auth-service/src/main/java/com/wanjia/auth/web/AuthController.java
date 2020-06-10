package com.wanjia.auth.web;

import com.wanjia.auth.config.JwtProperties;
import com.wanjia.auth.service.AuthService;
import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.common.pojo.UserInfo;
import com.wanjia.common.utils.CookieUtils;
import com.wanjia.common.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JT.L
 * @date 2020/5/30 11:13:42
 * @description
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties prop;

    /**
     * 登录授权
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username") String username, @RequestParam("password") String password,
                                         HttpServletRequest request, HttpServletResponse response) {
        String token = authService.accredit(username, password);

        if (StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CookieUtils.setCookie(request, response, prop.getCookieName(), token, prop.getCookieMaxAge());

        return ResponseEntity.ok().build();
    }

    /**
     * 校验用户登录状态
     *
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("WJ_TOKEN") String token,
                                           HttpServletRequest request, HttpServletResponse response) {
        /*if (StringUtils.isBlank(token)) {
            // 如果没有token，证明未登录，返回403
            throw new WjException(ExceptionEnum.UNAUTHORIZED);
        }*/
        try {
            // 解析token
            UserInfo info = JwtUtils.getInfoFromToken(token, prop.getPublicKey());

            // 刷新token，重新生成token
            String newToken = JwtUtils.generateToken(info, prop.getPrivateKey(), prop.getExpire());
            // 写回cookie
            CookieUtils.setCookie(request, response, prop.getCookieName(), newToken, prop.getCookieMaxAge());

            // 已登录，返回用户信息
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            // token已过期，或者token被篡改
            throw new WjException(ExceptionEnum.UNAUTHORIZED);
        }
    }

}
