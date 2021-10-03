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
 * @create: 2021-10-03 20:09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {

    @ApiModelProperty("PartyUser 属性")
    private PartyUser main;

    @ApiModelProperty("PartyTeacher 或 PartyStudent 属性")
    private UserRole details;

}
