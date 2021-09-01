package com.dingdong.party.admin.service;

import com.dingdong.party.admin.entity.PartyAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.admin.entity.vo.AdminEntity;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface PartyAdminService extends IService<PartyAdmin> {

    PartyAdmin queryByUsername(String username);

    boolean updateUserById(String userId, Integer status, String statusReason);

    Object info(String userId);

    boolean examineActivity(String activityId);

    boolean rejectActivity(String activityId);

    boolean partakeActivitySuccess(String userId, String activityId);

    boolean partakeActivityFail(String userId, String activityId);

    boolean participateActivitySuccess(String userId, String activityId);

    boolean participateActivityFail(String userId, String activityId);

    boolean changePassword(String userName, String oldPwd, String newPwd);

    boolean create(PartyAdmin admin);

    Map<String, Object> getList(String groupId, String branchId, Integer authority, Integer page, Integer size);

    AdminEntity queryNameByUserId(String userId);
}
