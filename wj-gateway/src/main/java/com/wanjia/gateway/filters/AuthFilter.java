package com.wanjia.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wanjia.common.pojo.UserInfo;
import com.wanjia.common.utils.CookieUtils;
import com.wanjia.common.utils.JwtUtils;
import com.wanjia.gateway.config.FilterProperties;
import com.wanjia.gateway.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JT.L
 * @date 2020/6/2 10:42:27
 * @description
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private FilterProperties filterProp;

    @Override
    public String filterType() {
        // 过滤器类型，前置过滤
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 获取请求的url路径
        String path = request.getRequestURI();
        // 判断是否放行，放行则返回false
        return !isAllowPath(path);
    }

    private boolean isAllowPath(String path) {
        for (String allowPath : filterProp.getAllowPaths()) {
            if (path.startsWith(allowPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = ctx.getRequest();
        // 获取cookie中的token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            // 解析token
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            // TODO 校验权限
        } catch (Exception e) {
            // 解析token失败，未登录，拦截
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(403);
        }
        return null;
    }
}
