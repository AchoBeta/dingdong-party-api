package com.dingdong.party.activity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@ApiModel(value="PartyActivity对象", description="")
@Accessors(chain = true)
public class PartyActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id", required = true)
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "活动名称", required = true)
    private String name;

    @ApiModelProperty(value = "摘要", required = true)
    private String summary;

    @ApiModelProperty(value = "党组id")
    private String groupId;

    @ApiModelProperty(value = "党支部id", required = true)
    private String branchId;

    @ApiModelProperty(value = "活动地点", required = true)
    private String address;

    @ApiModelProperty(value = "负责人id", required = true)
    private String directorId;

    @ApiModelProperty(value = "负责人姓名", required = true)
    private String directorName;

    @ApiModelProperty(value = "参与活动的人数")
    private Integer num;

    @ApiModelProperty(value = "限制人数")
    private Integer limitNum;

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "开始时间", required = true)
    private Date startTime;

    @ApiModelProperty(value = "截至时间", required = true)
    private Date endTime;

    @ApiModelProperty(value = "报名截至时间", required = true)
    private Date registrationEndTime;

    @ApiModelProperty(value = "状态(0:草稿 1:未审批 2:被驳回 3:已审批  4:可参与)", required = true)
    private Integer status;

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

    public String getStartTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(startTime);
    }

    public void setStartTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            startTime = format.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStartTime(Date time) {
        startTime = time;
    }

    public String getEndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(endTime);
    }

    public void setEndTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            endTime = format.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSEndTime(Date time) {
        endTime = time;
    }
}
