package com.dingdong.party.admin.security.server;

import com.dingdong.party.commonUtils.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销成功
 *
 * @author retraci
 */
@Component("logoutSuccessHandler")
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 清除 token
        String token = httpServletRequest.getHeader("token");
        redisTemplate.delete("admin:" + token);
        Result result = Result.ok().message("注销成功");
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
