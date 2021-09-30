package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyUserActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.activity.entity.vo.UserEntity;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-17
 */
public interface PartyUserActivityService extends IService<PartyUserActivity> {

    /**
     * 添加一些用户
     * @param userIds 用户id列表
     * @param activityId 活动id
     * @param branchId 党支部id
     * @param groupId 党组id
     * @return 是否成功
     */
    boolean addUsers(List<String> userIds, String activityId, String branchId, String groupId);

    /**
     * 根据活动查找所有用户
     * @param activityId 活动id
     * @return 所有用户
     */
    List<UserEntity> getAllUser(String activityId);

    /**
     * 活动请假
     * @param activityId 活动id
     * @param userId 用户id
     * @param reason 原因
     * @return 是否成功
     */
    boolean leave(String activityId, String userId, String reason);

    /**
     * 申请参与活动
     * @param activityId 活动id
     * @param userId 用户id
     * @return 是否成功
     */
    boolean participate(String activityId, String userId);

    /**
     * 查询活动人员
     * @param userId 用户id
     * @param activityId 活动id
     * @param status 状态
     * @param branchId 党支部id
     * @param groupId 党组id
     * @param page 当前页号
     * @param size 页面大小
     * @return 查询结果
     */
    HashMap<String, Object> query(String userId, String activityId, Integer status, String branchId, String groupId, Integer page, Integer size);

}
