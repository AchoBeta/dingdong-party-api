package com.dingdong.party.user.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="Student对象", description="")
public class StudentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户姓名", required = true)
    private String name;

    @ApiModelProperty(value = "学号", required = true)
    private String studentId;

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

    @ApiModelProperty(value = "学院")
    private String institute;

    @ApiModelProperty(value = "年级")
    private Integer grade;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "班级")
    private String className;

    @ApiModelProperty(value = "班级职务")
    private String classPosition;

    @ApiModelProperty(value = "社区")
    private String dormitoryArea;

    @ApiModelProperty(value = "宿舍号")
    private byte[] dormitoryNo;

    @ApiModelProperty(value = "性别(男:1,女:0)")
    private Boolean gender;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "籍贯")
    private String origin;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "入团时间")
    private Date joinLeagueTime;

    @ApiModelProperty(value = "家庭住址")
    private String familyAddress;

}
