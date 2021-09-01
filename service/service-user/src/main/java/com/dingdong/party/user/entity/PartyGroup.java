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
@ApiModel(value="PartyGroup对象", description="")
public class PartyGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "党小组id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "党小组名称")
    private String name;

    @ApiModelProperty(value = "党支部id")
    private String branchId;

    @ApiModelProperty(value = "党支部名称")
    private String branchName;

    @ApiModelProperty(value = "负责人id")
    private String directorId;

    @ApiModelProperty(value = "负责人名称")
    private String directorName;

    @ApiModelProperty(value = "逻辑删除")
    @JsonIgnore
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "删除时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
