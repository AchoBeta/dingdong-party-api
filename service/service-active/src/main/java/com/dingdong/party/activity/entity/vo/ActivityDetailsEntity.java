package com.dingdong.party.activity.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author retraci
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ActivityDetailsEntity", description="")
@Accessors(chain = true)
public class ActivityDetailsEntity {

    @ApiModelProperty(value = "活动id")
    private String id;

    @ApiModelProperty(value = "活动名称", required = true)
    private String name;

    @ApiModelProperty(value = "摘要", required = true)
    private String summary;

    @ApiModelProperty(value = "党组id", required = true)
    private String groupId;

    @ApiModelProperty(value = "党支部id")
    private String branchId;

    @ApiModelProperty(value = "活动地点", required = true)
    private String address;

    @ApiModelProperty(value = "负责人id", required = true)
    private String directorId;

    @ApiModelProperty(value = "负责人姓名", required = true)
    private String directorName;

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "人数", required = true)
    private Integer num;

    @ApiModelProperty(value = "限制人数", required = true)
    private Integer limitNum;

    @ApiModelProperty(value = "状态(0:草稿 1:未审批 2:已审批 3:被驳回)", required = true)
    private Integer status;

    @ApiModelProperty(value = "开始时间", required = true)
    private String startTime;

    @ApiModelProperty(value = "截至时间", required = true)
    private String endTime;

    @ApiModelProperty(value = "报名截至时间", required = true)
    private Date registrationEndTime;

    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @ApiModelProperty(value = "注意事项")
    private String attention;

    @ApiModelProperty(value = "公告链接")
    private String announcement;

    @ApiModelProperty(value = "附件链接")
    private String enclosure;

}
