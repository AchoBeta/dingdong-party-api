package com.dingdong.party.admin.security.server;

import com.alibaba.fastjson.JSONObject;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.serviceBase.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author retraci
 */
@Slf4j
@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {

    @Value("${application.login-url}")
    private String loginUrl;
    @Value("${application.image-code-url}")
    private String imageCodeUrl;

    @Resource
    private AuthenticationFailHandlerImpl loginFailureHandler;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        response.setContentType("text/json;charset=utf-8");
        if (url.equals(loginUrl)) {
            try {
                if (!validate(request, response)) {
                    return;
                }
            } catch (AuthenticationException e) {
                loginFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        } else {
            // ??????token,??????????????????????????????token
            if (url.contains("swagger") || url.contains("v2") || url.contains("webjars") || url.contains("favicon") || url.contains("doc.html")) {
                filterChain.doFilter(request, response);
                return;
            }
            if (!url.contains(imageCodeUrl)) {
                if (!validateToken(request, response)) {
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * ??????token
     */
    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ?????????????????????token
        String token = request.getHeader("token");
        // ??????token??????????????????
        String username = jwtUtils.getUsernameFromToken(token);
        //??????token????????????????????????????????????????????????
        if (StringUtils.isBlank(token) || StringUtils.isBlank(username)) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new Result().code(4001).message("token?????????????????????????????????")));
            return false;
        }
        // ?????????????????????????????????????????????????????????
        String details = (String) redisTemplate.opsForValue().get("admin:" + username);

        AdminEntity userDetails = JSONObject.parseObject(details, AdminEntity.class);
        if (userDetails == null) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new Result().code(4001).message("token?????????????????????????????????")));
            return false;
        }
        // ????????????????????????????????????????????????
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("admin"));
        userDetails.setPermissions(list);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // ??????????????????
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    /**
     * ???????????????
     */
    private boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.??????????????????????????????
        String inputCode = request.getParameter("code");
        // 2.???????????????????????????
        if (StringUtils.isBlank(inputCode)) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new Result().code(4001).message("?????????????????????!")));
            return false;
        }
        // 3. ???????????? redis ??????????????????
        if (redisTemplate.opsForValue().size(inputCode) == 0) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new Result().code(4001).message("?????????????????????????????????!")));
            return false;
        }
        redisTemplate.delete(inputCode);
        return true;
    }
}

