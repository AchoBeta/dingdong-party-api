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
@ApiModel(value="PartyStage对象", description="")
public class PartyStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "入党阶段id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    @ApiModelProperty(value = "入党阶段名称")
    private String name;

    @ApiModelProperty(value = "入党阶段描述")
    private String description;

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
