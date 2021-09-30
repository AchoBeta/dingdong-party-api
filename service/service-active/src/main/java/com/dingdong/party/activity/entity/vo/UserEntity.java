package com.dingdong.party.activity.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author retraci
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserActivityEntity", description="")
public class UserEntity {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "老师id")
    private String teacherId;

    @ApiModelProperty(value = "党支部id")
    private String branchId;

    @ApiModelProperty(value = "党支部名称")
    private String branchName;

    @ApiModelProperty(value = "党组id")
    private String groupId;

    @ApiModelProperty(value = "党组名称")
    private String groupName;

    @ApiModelProperty(value = "所属阶段")
    private Integer stageId;

}
