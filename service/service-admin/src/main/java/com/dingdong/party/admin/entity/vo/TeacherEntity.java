package com.dingdong.party.admin.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="GroupAdminEntity", description="")
public class TeacherEntity {

    @ApiModelProperty(value = "老师id")
    private String teacherId;

    @ApiModelProperty(value = "党龄")
    private Integer partyAge;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
