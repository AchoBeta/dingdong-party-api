package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.*;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyUserService extends IService<PartyUser> {

    /**
     * 通过 branchId 查询用户id
     * @param branchId
     * @return
     */
    List<String> queryByBranchId(String branchId);

    /**
     * 通过 groupId 查询用户id
     * @param groupId
     * @return
     */
    List<String> queryUserByGroupId(String groupId);

    /**
     * 通过 openId 查询用户
     * @param openId
     * @return
     */
    PartyUser queryByOpenId(String openId);

    /**
     * 查看用户信息
     * @param userId
     * @return
     */
    UserInfoVO info(String userId);

    /**
     * 分页
     * @param branchId
     * @param groupId
     * @param stageId
     * @param periodsNum
     * @param institute
     * @param grade
     * @param major
     * @param page
     * @param size
     * @return
     */
    List<PartyUser> getList(String branchId, String groupId, Integer stageId, Integer periodsNum, String institute, String grade, String major, Integer page, Integer size);

    /**
     * 查看用户参与的活动
     * @param userId
     * @param status
     * @param activityId
     * @return
     */
    List<UserActivityEntity> getActivities(String userId, Integer status, String activityId);

    /**
     * 查看用户的评论
     * @param userId
     * @return
     */
    List<CommentEntity> getComments(String userId);

    /**
     * 创建
     * @param user
     */
    void create(PartyUser user);

    /**
     * 删除
     * @param id
     */
    void remove(String id);

    /**
     * 添加老师信息
     * @param userId
     * @param teacherEntity
     */
    void updateTeacher(String userId, TeacherEntity teacherEntity);

    /**
     * 添加学生信息
     * @param userId
     * @param studentEntity
     */
    void updateStudent(String userId, StudentEntity studentEntity);
}
