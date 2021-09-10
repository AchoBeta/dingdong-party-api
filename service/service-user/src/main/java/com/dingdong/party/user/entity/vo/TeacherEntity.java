package com.dingdong.party.user.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="Teacher对象", description="")
public class TeacherEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户姓名", required = true)
    private String name;

    @ApiModelProperty(value = "老师id", required = true)
    private String teacherId;

    @ApiModelProperty(value = "党支部id", required = true)
    private String branchId;

    @ApiModelProperty(value = "党支部名称", required = true)
    private String branchName;

    @ApiModelProperty(value = "党组id", required = true)
    private String groupId;

    @ApiModelProperty(value = "党组名称", required = true)
    private String groupName;

    @ApiModelProperty(value = "党龄", required = true)
    private Integer partyAge;

    @ApiModelProperty(value = "电话", required = true)
    private String phone;

    @ApiModelProperty(value = "邮箱",required = true)
    private String email;

}
