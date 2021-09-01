package com.dingdong.party.user.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户活动", description="")
public class UserActivityEntity {

    @ApiModelProperty(value = "活动id", required = true)
    private String id;

    @ApiModelProperty(value = "活动名称", required = true)
    private String name;

    @ApiModelProperty(value = "活动简介", required = true)
    private String summary;

    @ApiModelProperty(value = "党组id", required = true)
    private String groupId;

    @ApiModelProperty(value = "党支部id", required = true)
    private String branchId;

    @ApiModelProperty(value = "活动地址", required = true)
    private String address;

    @ApiModelProperty(value = "负责人id", required = true)
    private String directorId;

    @ApiModelProperty(value = "负责人姓名", required = true)
    private String directorName;

    @ApiModelProperty(value = "参与人数")
    private Integer num;

    @ApiModelProperty(value = "限制人数")
    private Integer limitNum;

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "请假状态：0：正常，1：请假")
    private Integer partStatus;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "截至时间")
    private String endTime;


}
