package com.dingdong.party.admin.entity.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminEntity对象", description="")
public class AdminEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员id", required = true)
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "教师id")
    private String teacherId;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "党组id")
    private String groupId;

    @ApiModelProperty(value = "党支部id")
    private String branchId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "账号是否可用")
    @JsonIgnore
    private boolean enabled;

    @ApiModelProperty(value = "权限")
    private Integer authority;

    @ApiModelProperty(value = "账号是否过期")
    @JsonIgnore
    private boolean accountNonExpired;

    @ApiModelProperty(value = "权限")
    @JsonIgnore
    private Collection<? extends GrantedAuthority> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
