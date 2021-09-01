package com.dingdong.party.admin.security.server;

import com.alibaba.fastjson.JSONObject;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.serviceBase.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {
    @Value("${application.login-url}")
    private String loginUrl;
    @Value("${application.image-code-url}")
    private String imageCodeUrl;

    @Autowired
    private AuthenticationFailHandlerImpl loginFailureHandler;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if(url.equals(loginUrl)){
            try{
                //validate(request);
            }catch (AuthenticationException e){
                loginFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }else {
            //验证token,验证码请求不需要验证token
            if (url.contains("swagger") || url.contains("v2") || url.contains("webjars") || url.contains("favicon") || url.contains("doc.html")) {
                filterChain.doFilter(request,response);
                return;
            }
            if (!url.contains(imageCodeUrl)) {
                validateToken(request);
            }
        }
        filterChain.doFilter(request,response);
    }
    //验证token
    private void validateToken(HttpServletRequest request){
        //获取前端传来的token
        String token = request.getHeader("token");
        //解析token，获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果token或者用户名为空的话，不能通过认证
        if(StringUtils.isBlank(token) || StringUtils.isBlank(username)){
            throw new RuntimeException("token验证失败!");
        }
        String details = (String) redisTemplate.opsForValue().get("admin:" + username);     // 登录时通过缓存拿到账户信息，保存此状态

        AdminEntity userDetails = JSONObject.parseObject(details, AdminEntity.class);
        if(userDetails == null){
            throw new RuntimeException("token验证失败!");
        }
        ArrayList<GrantedAuthority> list = new ArrayList<>();        // 需要改进，缓存中也需携带权限信息
        list.add(new SimpleGrantedAuthority("admin"));
        userDetails.setPermissions(list);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置为已登录
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    //验证验证码
    private void validate(HttpServletRequest request){
        //1.获取登录请求的验证码
        String inputCode = request.getParameter("code");
        //2.获取Session中的验证码
        String  code = (String)request.getSession().getAttribute("IMAGE_CODE");
        //3.判断验证码是否为空
        if(StringUtils.isBlank(inputCode)){
            throw new RuntimeException("验证码不能为空!");
        }
        //4.判断验证码是否相等
        if(!inputCode.equalsIgnoreCase(code)){
            throw new RuntimeException("验证码输入错误!");
        }
    }
}

