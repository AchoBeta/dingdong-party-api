package com.dingdong.party.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.admin.entity.PartyUserStage;
import com.dingdong.party.admin.mapper.PartyUserStageMapper;
import com.dingdong.party.admin.service.PartyUserStageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
@Service
public class PartyUserStageServiceImpl extends ServiceImpl<PartyUserStageMapper, PartyUserStage> implements PartyUserStageService {

    @Autowired
    PartyUserStageMapper userStageMapper;

    /**
     * 按用户 id 修改/增加阶段
     * @param userIds
     * @param stageId
     * @param time
     * @return
     */
    @Override
    public boolean updateStageByUserIds(String[] userIds, Integer stageId, Date time) {
        for (String userId : userIds) {
            QueryWrapper<PartyUserStage> wrapper = new QueryWrapper<>();
            if (!userStageMapper.updateStage(userId, stageId) || !updateOrSave(wrapper, userId, stageId, time))   // 修改用户表的阶段
                return false;
        }
        return true;
    }

    /**
     * 按条件 - 用户id 修改/增加阶段
     * @param branchId
     * @param groupId
     * @param stage
     * @param stageId
     * @param userIds
     * @param time
     * @return
     */
    @Override
    public boolean updateStageByCondition(String branchId, String groupId, Integer stage, Integer stageId, String[] userIds, Date time) {
        Set<String> userIdSet = new HashSet<>();
        for (String userId : userIds) {
            userIdSet.add(userId);
        }
        List<String> userIdList = userStageMapper.getUser(branchId, groupId, stage);  // 获取需要改变的用户
        QueryWrapper<PartyUserStage> wrapper = new QueryWrapper<>();
        for (String userId : userIdList) {
            if (userIdSet.contains(userId))   // 去掉选中的用户
                continue;
            if (!userStageMapper.updateStage(userId, stageId) || !updateOrSave(wrapper, userId, stageId, time))   // 修改用户表的阶段
                return false;
        }
        return true;
    }

    public boolean updateOrSave(QueryWrapper<PartyUserStage> wrapper, String userId, Integer stageId, Date time) {
        wrapper.eq("user_id", userId);
        wrapper.eq("stage_id", stageId);
        PartyUserStage userStage;
        if ((userStage = userStageMapper.selectOne(wrapper)) != null) {  // 判断是修改阶段还是增加阶段
            userStage.setTime(time);
            userStageMapper.updateById(userStage);
        } else {
            userStage = new PartyUserStage();
            userStage.setUserId(userId).setStageId(stageId).setTime(time);
            userStageMapper.insert(userStage);
        }
        return true;
    }
}
