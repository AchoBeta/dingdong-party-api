package com.dingdong.party.user.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author retraci
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CommentEntity对象", description="")
public class CommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id", required = true)
    private String id;

    @ApiModelProperty(value = "活动id", required = true)
    private String activityId;

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "评论内容", required = true)
    private String content;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "姓名")
    private String name;
}
