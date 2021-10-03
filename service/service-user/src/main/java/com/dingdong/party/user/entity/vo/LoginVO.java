package com.dingdong.party.user.entity.vo;

import com.dingdong.party.user.entity.PartyUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: api
 * @author: retraci
 * @create: 2021-10-03 20:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO implements Serializable {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("用户信息")
    private PartyUser item;

}
