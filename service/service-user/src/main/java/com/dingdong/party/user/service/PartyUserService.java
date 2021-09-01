package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.CommentEntity;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.entity.vo.TeacherEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface PartyUserService extends IService<PartyUser> {

    List<String> queryByBranchId(String branchId);

    List<String> queryUserByGroupId(String groupId);

    PartyUser queryByOpenId(String openId);

    boolean updateTeacher(String userId, TeacherEntity teacherEntity);

    boolean updateStudent(String userId, StudentEntity studentEntity);

    Map<String, Object> info(String userId);

    Map<String, Object> getList(String branchId, String groupId, Integer stageId, Integer periodsNum, String institute, String grade, String major, Integer page, Integer size);

    Object getActivities(String userId, Integer status, String activityId);

    List<CommentEntity> getComments(String userId);
}
