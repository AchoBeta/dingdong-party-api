package com.dingdong.party.user.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PartyUser对象", description="")
public class PartyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "user_id", type = IdType.ID_WORKER_STR)
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "微信id")
    private String openId;

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

    @ApiModelProperty(value = "阶段任务")
    private Integer taskId;

    @ApiModelProperty(value = "信息审核状态，1:通过 2:不通过")
    private Integer status;

    @ApiModelProperty(value = "不通过原因")
    private String statusReason;

    @ApiModelProperty(value = "逻辑删除")
    @JsonIgnore
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
