package com.dingdong.party.user.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(value="StageCount对象", description="")
public class StageCountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "入党阶段id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    @ApiModelProperty(value = "入党阶段名称")
    private String name;

    @ApiModelProperty(value = "入党阶段描述")
    private String description;

    @ApiModelProperty(value = "总支部Id")
    private Integer generalBranchId;

    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiModelProperty(value = "总人数")
    private Integer total;
}
