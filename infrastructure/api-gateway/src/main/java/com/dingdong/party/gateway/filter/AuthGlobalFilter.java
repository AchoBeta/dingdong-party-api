package com.dingdong.party.gateway.filter;

import com.dingdong.party.serviceBase.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 * </p>
 *
 * @author qy
 * @since 2019-11-21
 */
@Component
public class AuthGlobalFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);

    JwtUtils jwtUtils = new JwtUtils();

    /**
     * 请求前处理
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUrl = request.getRequestURL().toString();
        // 排除登录，登出和 swagger
        return !requestUrl.contains("login") && !requestUrl.contains("logout") && !requestUrl.contains("backstage") && !requestUrl.contains("api-docs");
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 获取传来的参数 token
        String token = request.getHeader("token");
        try {
            if (token == null) {
                log.warn("Authorization token is empty");
                // 过滤该请求，不往下级服务去转发请求，到此结束
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(4003);
                ctx.setResponseBody("Authorization is empty!");
            } else {
                if (!jwtUtils.isTokenExpired(token)) {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(4004);
                    ctx.setResponseBody("Authorization is overdue!");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            return null;
        }
    }
}

