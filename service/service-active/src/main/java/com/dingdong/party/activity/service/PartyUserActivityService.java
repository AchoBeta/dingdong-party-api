package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyUser;
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

    boolean addUsers(List<String> userIds, String activityId, String branchId, String groupId);

    List<UserEntity> getAllUser(String activityId);

    boolean leave(String activityId, String userId, String reason);

    boolean participate(String activityId, String userId);

    HashMap<String, Object> query(String userId, String activityId, Integer status, String branchId, String groupId, Integer page, Integer size);

}
