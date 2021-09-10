package com.dingdong.party.user.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Task对象", description="")
public class TaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "入党任务id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    @ApiModelProperty(value = "入党任务名称")
    private String name;

    @ApiModelProperty(value = "入党任务介绍")
    private String descript;

    @ApiModelProperty(value = "所属阶段")
    private Integer stageId;

}
