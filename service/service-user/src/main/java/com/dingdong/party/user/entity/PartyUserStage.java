package com.dingdong.party.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PartyUserStage对象", description="")
public class PartyUserStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户-阶段id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "阶段id")
    private Integer stageId;

    @ApiModelProperty(value = "时间")
    private Date time;

    @ApiModelProperty(value = "逻辑删除")
    @JsonIgnore
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonIgnore
    private Date modifyTime;


}
