package com.dingdong.party.activity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PartyActivityImage对象", description="")
@Accessors(chain = true)
public class PartyActivityImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片id", required = true)
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "活动id", required = true)
    private String activityId;

    @ApiModelProperty(value = "图片地址", required = true)
    private String path;

    @ApiModelProperty(value = "第几张", required = true)
    private Integer sort;

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
