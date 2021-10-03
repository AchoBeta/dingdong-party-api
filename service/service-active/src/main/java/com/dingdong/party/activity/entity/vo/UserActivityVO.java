package com.dingdong.party.activity.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: api
 * @author: retraci
 * @create: 2021-10-04 00:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityVO implements Serializable {

    @ApiModelProperty(value = "活动id", required = true)
    private String activityId;

    @ApiModelProperty(value = "状态（0：正常，1：请假 2:审批通过）", required = true)
    private Integer status;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "老师id")
    private String teacherId;

    @ApiModelProperty(value = "期数")
    private Integer stage;

    @ApiModelProperty(value = "党组id")
    private String groupId;

    @ApiModelProperty(value = "党组名称")
    private String groupName;

    @ApiModelProperty(value = "党支部id")
    private String branchId;

    @ApiModelProperty(value = "党支部名称")
    private String branchName;

    @ApiModelProperty(value = "是否是老师")
    private Boolean isTeacher;

}
