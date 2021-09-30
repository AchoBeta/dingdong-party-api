package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dingdong.party.activity.entity.PartyActivity;
import com.dingdong.party.activity.entity.PartyUserActivity;
import com.dingdong.party.activity.entity.vo.UserEntity;
import com.dingdong.party.activity.mapper.PartyActivityMapper;
import com.dingdong.party.activity.mapper.PartyUserActivityMapper;
import com.dingdong.party.activity.service.PartyUserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.page.PageMethod;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-17
 */
@Service
public class PartyUserActivityServiceImpl extends ServiceImpl<PartyUserActivityMapper, PartyUserActivity> implements PartyUserActivityService {

    @Resource
    private PartyUserActivityMapper userActivityMapper;

    @Resource
    private PartyActivityMapper partyActivityMapper;

    @Resource
    private PartyActivityServiceImpl partyActivityService;

    @Override
    public boolean addUsers(List<String> userIds, String activityId, String branchId, String groupId) {
        PartyUserActivity partyUserActivity = new PartyUserActivity();
        for (String userId : userIds) {
            partyUserActivity.setActivityId(activityId);
            partyUserActivity.setUserId(userId);
            partyUserActivity.setBranchId(branchId);
            partyUserActivity.setGroupId(groupId);
            if (!this.save(partyUserActivity)) {
                return false;
            }
            partyUserActivity.setId(null);
        }
        UpdateWrapper<PartyActivity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", activityId);
        wrapper.set("num", userIds.size());
        partyActivityMapper.update(null, wrapper);
        return true;
    }

    @Override
    public List<UserEntity> getAllUser(String activityId) {
        return userActivityMapper.getAllUser(activityId);
    }

    @Override
    public boolean leave(String activityId, String userId, String reason) {
        System.out.println(reason);
        return userActivityMapper.leave(activityId, userId, reason);
    }

    @Override
    public boolean participate(String activityId, String userId) {
        PartyActivity activity = partyActivityService.getById(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        if (System.currentTimeMillis() > activity.getRegistrationEndTime().getTime()) {
            throw new RuntimeException("活动报名已截止");
        }

        PartyUserActivity userActivity = new PartyUserActivity();
        userActivity.setUserId(userId).setActivityId(activityId).setStatus(4);
        return this.save(userActivity);
    }

    @Override
    public HashMap<String, Object> query(String userId, String activityId, Integer status, String branchId, String groupId, Integer page, Integer size) {
        PageMethod.startPage(page, size);
        List<Map<String, Object>> res = userActivityMapper.query(userId, activityId, status, branchId, groupId);

        for (Map<String, Object> user : res) {
            user.put("isTeacher", user.get("teacherId") != null);
        }

//        QueryWrapper<PartyUserActivity> wrapper = new QueryWrapper<>();
//        if (userId != null)
//            wrapper.eq("user_id", userId);
//        if (activityId != null)
//            wrapper.eq("activity_id", activityId);
//        if (status != null)
//            wrapper.eq("status", status);
//        if (branchId != null)
//            wrapper.eq("branch_id", branchId);
//        if (groupId != null)
//            wrapper.eq("group_id", groupId);
//        Page<PartyUserActivity> userActivityPage = new Page<>(page, size);
//        this.page(userActivityPage, wrapper);

        long total = res.size();
        if (total == 0) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", res);
        return map;
    }
}
