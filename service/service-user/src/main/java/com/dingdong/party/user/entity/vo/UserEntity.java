package com.dingdong.party.user.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
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

    @ApiModelProperty(value = "期数")
    private Integer stage;

    @ApiModelProperty(value = "信息审核状态，0:未审核 1:通过 2:不通过")
    private Integer status;

    @ApiModelProperty(value = "学院")
    private String institute;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "参与活动数")
    private Integer num;

}
