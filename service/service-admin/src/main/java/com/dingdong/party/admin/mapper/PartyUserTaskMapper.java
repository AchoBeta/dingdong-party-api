package com.dingdong.party.admin.mapper;

import com.dingdong.party.admin.entity.PartyUserTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface PartyUserTaskMapper extends BaseMapper<PartyUserTask> {

    @Update("update party_user set task_id = #{arg1} where user_id = #{arg0}")
    boolean updateUserTask(String userId, Integer taskId);

    @Update("update party_user_task set time = #{arg2} where user_id = #{arg0} and task_id = #{arg1}")
    boolean updateTime(String userId, String taskId, Date time);
}
