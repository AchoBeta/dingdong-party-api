package com.dingdong.party.admin.security.server;

import com.alibaba.fastjson.JSONObject;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.serviceBase.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理器
 */
@Component("authenticationSuccessHandler")
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
         AdminEntity userDetails = (AdminEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 生成 token，返回并放入 redis
        String token = jwtUtils.generateToken(userDetails.getUsername());

        Result result = Result.ok().data("token", token).data("items", userDetails);

        String jsonBDID = JSONObject.toJSONString(userDetails);
        redisTemplate.opsForValue().set("admin:" + userDetails.getUsername(), jsonBDID);   // 将其放到缓存，每次登录时都拿这个记录状态
        redisTemplate.expire("admin:" + userDetails.getUsername(), 20, TimeUnit.MINUTES);

        // 处理编码方式
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
