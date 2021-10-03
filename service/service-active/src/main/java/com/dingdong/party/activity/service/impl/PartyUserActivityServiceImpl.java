package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dingdong.party.activity.entity.PartyActivity;
import com.dingdong.party.activity.entity.PartyUserActivity;
import com.dingdong.party.activity.entity.vo.UserActivityVO;
import com.dingdong.party.activity.entity.vo.UserEntity;
import com.dingdong.party.activity.mapper.PartyActivityMapper;
import com.dingdong.party.activity.mapper.PartyUserActivityMapper;
import com.dingdong.party.activity.service.PartyUserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.github.pagehelper.PageHelper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
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
    public void addUsers(List<String> userIds, String activityId, String branchId, String groupId) {
        List<PartyUserActivity> list = userIds.stream().map(userId -> {
            PartyUserActivity partyUserActivity = new PartyUserActivity();
            partyUserActivity.setActivityId(activityId);
            partyUserActivity.setUserId(userId);
            partyUserActivity.setBranchId(branchId);
            partyUserActivity.setGroupId(groupId);
            return partyUserActivity;
        }).collect(Collectors.toList());
        boolean res = this.saveBatch(list);
        if (!res) {
            throw new PartyException("添加失败", ResultCode.COMMON_FAIL.getCode());
        }

        UpdateWrapper<PartyActivity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", activityId);
        wrapper.set("num", userIds.size());
        int cnt = partyActivityMapper.update(null, wrapper);
        if (cnt == 0) {
            throw new PartyException("添加失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public List<UserEntity> getAllUser(String activityId) {
        List<UserEntity> list = userActivityMapper.getAllUser(activityId);
        if (list.size() <= 0) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return list;
    }

    @Override
    public void leave(String activityId, String userId, String reason) {
        boolean res = userActivityMapper.leave(activityId, userId, reason);
        if (!res) {
            throw new PartyException("请假失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void participate(String activityId, String userId) {
        PartyActivity activity = partyActivityService.getById(activityId);
        if (activity == null) {
            throw new PartyException("活动不存在", ResultCode.COMMON_FAIL.getCode());
        }

        if (System.currentTimeMillis() > activity.getRegistrationEndTime().getTime()) {
            throw new PartyException("活动报名已截止", ResultCode.COMMON_FAIL.getCode());
        }

        PartyUserActivity userActivity = new PartyUserActivity();
        userActivity.setUserId(userId).setActivityId(activityId).setStatus(4);

        boolean res = this.save(userActivity);
        if (!res) {
            throw new PartyException("申请参与失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public List<UserActivityVO> query(String userId, String activityId, Integer status, String branchId, String groupId, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        List<UserActivityVO> list = userActivityMapper.query(userId, activityId, status, branchId, groupId);
        for (UserActivityVO user : list) {
            user.setIsTeacher(user.getTeacherId() != null);
        }

        return list;
    }
}
