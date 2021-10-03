package com.dingdong.party.user.mapper;

import com.dingdong.party.user.entity.PartyUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.user.entity.vo.CommentEntity;
import com.dingdong.party.user.entity.vo.UserActivityEntity;
import com.dingdong.party.user.entity.vo.UserEntity;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyUserMapper extends BaseMapper<PartyUser> {

    /**
     * 根据 branchId 查询所有用户
     * @param branchId
     * @return
     */
    @Select("select id from party_user where branch_id = #{branchId}")
    List<String> queryUserByBranchId(String branchId);

    /**
     * 根据 groupId 查询所有用户
     * @param groupId
     * @return
     */
    @Select("select id from party_user where group_id = #{groupId}")
    List<String> queryUserByGroupId(String groupId);

    /**
     * 查询用户参与的活动
     * @param userId
     * @param status
     * @return
     */
    @Select("select b.*, a.partStatus " +
            "from " +
            "(select activity_id, status as partStatus from party_user_activity " +
            "where user_id = #{arg0}) as a, " +
            "(select id, name, summary, group_id as groupId, branch_id as branchId, address, director_id as directorId," +
            "director_name as directorName, email, status, num, limit_num as limitNum, start_time as startTime, end_time as endTime from party_activity where status = #{arg1}) as b " +
            "where a.activity_id = b.id")
    List<UserActivityEntity> getUserActivity(String userId, Integer status);

    /**
     * 获取用户的所有活动
     * @param userId
     * @return
     */
    @Select("select b.*, a.partStatus " +
            "from " +
            "(select activity_id, status as partStatus from party_user_activity " +
            "where user_id = #{userId}) as a, " +
            "(select id, name, summary, group_id as groupId, branch_id as branchId, address, director_id as directorId," +
            "director_name as directorName, email, status, num, limit_num as limitNum, start_time as startTime, end_time as endTime from party_activity " +
            "where is_deleted = 0 and status > 2) as b " +
            "where a.activity_id = b.id")
    List<UserActivityEntity> getUserAllActivity(String userId);

    /**
     * 获取一个活动
     * @param userId
     * @param activityId
     * @return
     */
    @Select("select * " +
            "from party_user_activity " +
            "where user_id = #{arg0} and activity_id = #{arg1}")
    List<UserActivityEntity> getOneActivity(String userId, String activityId);

    /**
     * 获取用户的学院，年级，专业
     */
    @Select("select institute, grade, major from party_student where student_id = #{studentId}")
    HashMap<String, String> selectDetails(String studentId);

    /**
     * 获取用户参与的期数
     * @param userId
     * @return
     */
    @Select("select count(*) from party_user_activity where user_id = #{userId} and status = 0 and is_deleted = 0")
    Integer getParticipatyNum(String userId);

    /**
     * 查看用户评论
     * @param userId
     * @return
     */
    @Select("select a.*, b.name from " +
            "(select activity_id as activityId, user_id as userId, content, image " +
            "from party_activity_comment where user_id = #{userId}) as a, " +
            "(select user_id, name from party_user where user_id = #{userId}) as b " +
            "where a.userId = b.user_id")
    List<CommentEntity> getComments(String userId);
}
