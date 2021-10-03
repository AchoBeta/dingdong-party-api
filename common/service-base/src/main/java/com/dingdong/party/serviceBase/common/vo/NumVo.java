package com.dingdong.party.serviceBase.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: api
 * @author: retraci
 * @create: 2021-10-03 21:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumVo implements Serializable {

    @ApiModelProperty("数字")
    private Integer num;

}
