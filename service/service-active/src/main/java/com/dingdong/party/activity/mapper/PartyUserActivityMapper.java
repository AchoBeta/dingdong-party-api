package com.dingdong.party.activity.mapper;

import com.dingdong.party.activity.entity.PartyUserActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.activity.entity.vo.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-17
 */
public interface PartyUserActivityMapper extends BaseMapper<PartyUserActivity> {

    @Select("select a.* from " +
            "(select user_id as userId, name, student_id as studentId, teacher_id as teacherId, branch_id as branchId, branch_name as branch_Name, group_id as groupId," +
            "group_name as groupName, stage_id as stageId, stage from party_user) as a, " +
            "(select user_id from party_user_activity where activity_id = #{activityId}) as b " +
            "where a.userId = b.user_id")
    List<UserEntity> getAllUser(String activityId);

    @Update("update party_user_activity set status = 1, reason = #{arg2} where user_id = #{arg1} and activity_id = #{arg0}")
    boolean leave(String activityId, String userId, String reason);
}