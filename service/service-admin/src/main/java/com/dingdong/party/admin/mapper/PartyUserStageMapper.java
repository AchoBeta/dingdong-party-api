package com.dingdong.party.admin.mapper;

import com.dingdong.party.admin.entity.PartyUserStage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
public interface PartyUserStageMapper extends BaseMapper<PartyUserStage> {

    // 获取阶段的第一个任务id
    @Select("SELECT id FROM party_task WHERE stage_id = #{stageId} LIMIT 0, 1")
    Integer getOneTask(Integer stageId);

    // 更新用户表的阶段
    @Update("update party_user set stage_id = #{arg1}, task_id = #{arg2} where user_id = #{arg0}")
    boolean updateUserStage(String userId, Integer stageId, Integer taskId);

    @Update("update party_user_stage set time = #{arg2} where user_id = #{arg0} and stage_id = #{arg1}")
    boolean updateTime(String userId, String stageId, Date time);
}
