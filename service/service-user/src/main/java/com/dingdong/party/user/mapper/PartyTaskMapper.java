package com.dingdong.party.user.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.user.entity.PartyTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.user.entity.vo.TaskEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyTaskMapper extends BaseMapper<PartyTask> {

    @Select("select id, name, descript, stage_id from party_task")
    List<TaskEntity> selectAll();

    @Select("select id, name, descript, stage_id from party_task where stage_id = #{stageId}")
    List<TaskEntity> select(String stageId);
}
