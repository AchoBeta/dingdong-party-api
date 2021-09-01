package com.dingdong.party.admin.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="GroupAdminEntity", description="")
public class StudentEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学号")
    @TableId(value = "student_id", type = IdType.ID_WORKER_STR)
    private String studentId;

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
