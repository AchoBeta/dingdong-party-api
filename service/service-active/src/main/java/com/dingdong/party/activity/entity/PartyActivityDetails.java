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
@ApiModel(value="PartyActivityDetails对象", description="")
@Accessors(chain = true)
public class PartyActivityDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id", required = true)
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @ApiModelProperty(value = "注意事项")
    private String attention;

    @ApiModelProperty(value = "公告链接")
    private String announcement;

    @ApiModelProperty(value = "附件链接·")
    private String enclosure;

    @ApiModelProperty(value = "逻辑删除")
    @JsonIgnore
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "删除时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date modifyTime;


}
