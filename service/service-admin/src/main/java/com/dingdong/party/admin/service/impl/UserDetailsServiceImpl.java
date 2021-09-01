package com.dingdong.party.admin.service.impl;

import com.dingdong.party.admin.entity.PartyAdmin;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.admin.service.PartyAdminService;
import com.dingdong.party.serviceBase.exception.PartyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * 用户认证
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PartyAdminService adminService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据账号获取管理员信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.length() == 0)
            throw new RuntimeException("用户名不能为空");
        // 如果不为空则根据用户名获取用户信息
        PartyAdmin admin = adminService.queryByUsername(username);

        // 判断管理员是否存在
        if (admin == null)
            throw new PartyException("用户名不存在", 4006);

        AdminEntity adminEntity = adminService.queryNameByUserId(admin.getUserId());
        BeanUtils.copyProperties(admin, adminEntity);

        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("admin"));

        adminEntity.setPermissions(list);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(adminEntity,null,adminEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return adminEntity;
    }
}

