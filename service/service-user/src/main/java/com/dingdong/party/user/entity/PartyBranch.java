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
@ApiModel(value="PartyBranch对象", description="")
public class PartyBranch implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总支部id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "总支部名称")
    private String name;

    @ApiModelProperty(value = "父级党支部id")
    private String parentId;

    @ApiModelProperty(value = "父级党支部名称")
    private String parentName;

    @ApiModelProperty(value = "负责人id")
    private String directorId;

    @ApiModelProperty(value = "负责人名字")
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
