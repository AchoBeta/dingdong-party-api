package com.dingdong.party.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.serviceBase.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import springfox.documentation.annotations.ApiIgnore;

public class BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JwtUtils jwtUtils;

    @ApiIgnore
    // 判断是否有权限做操作
    public int getGroupAdminAuthority(String token, String groupId) {
        //解析token，获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果token或者用户名为空的话，不能通过认证
        if(StringUtils.isBlank(token) || StringUtils.isBlank(username)){
            throw new RuntimeException("token验证失败!");
        }
        String details = (String) redisTemplate.opsForValue().get("admin:" + username);     // 登录时通过缓存拿到账户信息，保存此状态
        AdminEntity userDetails = JSONObject.parseObject(details, AdminEntity.class);
        if (userDetails.getGroupId().equals(groupId))
            return userDetails.getAuthority();
        return -1;
    }

    public int getBranchAdminAuthority(String token, String branchId) {
        //解析token，获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果token或者用户名为空的话，不能通过认证
        if(StringUtils.isBlank(token) || StringUtils.isBlank(username)){
            throw new RuntimeException("token验证失败!");
        }
        String details = (String) redisTemplate.opsForValue().get("admin:" + username);     // 登录时通过缓存拿到账户信息，保存此状态
        AdminEntity userDetails = JSONObject.parseObject(details, AdminEntity.class);
        if (userDetails.getGroupId() != null)
            return -1;
        if (userDetails.getBranchId().equals(branchId))
            return userDetails.getAuthority();
        return -1;
    }
}
