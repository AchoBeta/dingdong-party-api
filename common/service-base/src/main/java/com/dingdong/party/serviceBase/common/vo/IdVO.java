package com.dingdong.party.serviceBase.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: api
 * @author: retraci
 * @create: 2021-10-03 19:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class IdVO implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

}
