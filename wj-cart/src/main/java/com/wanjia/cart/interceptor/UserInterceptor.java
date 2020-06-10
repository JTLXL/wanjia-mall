package com.wanjia.cart.interceptor;

import com.wanjia.cart.config.JwtProperties;
import com.wanjia.common.pojo.UserInfo;
import com.wanjia.common.utils.CookieUtils;
import com.wanjia.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JT.L
 * @date 2020/6/7 16:16:16
 * @description
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    private JwtProperties prop;

    private static final ThreadLocal<UserInfo> TL = new ThreadLocal<>();

    public UserInterceptor(JwtProperties prop) {
        this.prop = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie中的token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            // 解析token
            UserInfo user = JwtUtils.getInfoFromToken(token, prop.getPublicKey());

            // 传递user
            // request.setAttribute("user", user);
            TL.set(user);

            return true;
        } catch (Exception e) {
            log.error("[购物车服务] 解析用户身份失败.", e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 最后用完数据，一定要清空
        TL.remove();
    }

    public static UserInfo getUser() {
        return TL.get();
    }
}
