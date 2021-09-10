package com.dingdong.party.admin.mapper;

import com.dingdong.party.admin.entity.PartyAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.admin.entity.vo.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Mapper
public interface PartyAdminMapper extends BaseMapper<PartyAdmin> {

    @Update("update party_user set status = #{arg1}, status_reason = #{arg2} where user_id = #{arg0}")
    boolean updateUserById(String userId, Integer status, String statusReason);

    // 判断此用户是学生还是老师
    @Select("select teacher_id as teacherId, student_id as studentId from party_user where user_id = #{userId}")
    HashMap<String, String> judgeTeacherStudent(String userId);

    // 得到学生信息
    @Select("select student_id as studentId, institute, grade, major, class_name as className, dormitory_area as dormitoryArea, " +
            "dormitory_no as dormitoryNo, gender, nation, origin, id_card AS idCard, birthday, phone, join_league_time as joinLeagueTime, " +
            "family_address as familyAddress from party_student where student_id = #{studentId}")
    StudentEntity getStudent(String studentId);

    // 得到老师信息
    @Select("select teacher_id as teacherId, party_age as partyAge, phone, email from party_teacher where teacher_id = #{teacherId}")
    TeacherEntity getTeacher(String teacherId);

    // 活动通过审核
    @Update("update party_activity set status = #{arg1} where id = #{arg0}")
    boolean examineActivity(String activityId, int status);

    // 活动被驳回
    @Update("update party_activity set status = 2 where id = #{activityId}")
    boolean rejectActivity(String activityId);

    // 用户-活动状态
    @Update("update party_user_activity set status = #{arg2} where user_id = #{arg0} and activity_id = #{arg1}")
    boolean examineUserActivity(String userId, String activityId, int i);

    // 获取管理员用户信息
    @Select("select name, student_id as studentId, teacher_id as teacherId " +
            "from party_user " +
            "where user_id = #{userId}")
    UserAdminEntity queryInfo(String userId);

    // 获取活动的日期
    @Select("select start_time as startTime, end_time as endTime from party_activity where id = #{activityId}")
    ActivityTimeEntity getActivityTime(String activityId);

    @Select("select name, student_id as studentId, teacher_id as teacherId from party_user where user_id = #{userId}")
    AdminEntity queryInfoByUserId(String userId);

    // 减少活动人数
    @Update("update party_activity set num = num + #{arg1} where id = #{arg0}")
    boolean changeActivityNum(String activityId, Integer num);
}