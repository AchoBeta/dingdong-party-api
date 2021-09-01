package com.dingdong.party.admin.service.impl;

import com.dingdong.party.admin.entity.PartyUserStage;
import com.dingdong.party.admin.mapper.PartyUserStageMapper;
import com.dingdong.party.admin.service.PartyUserStageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // 批量改变用户阶段
    @Override
    public boolean changeUserStage(String[] userIds, Integer stageId, Date time) {
        int n = userIds.length;
        List<PartyUserStage> stages = new ArrayList<>();
        for (int i = 0; i < n; i++)
            stages.add(new PartyUserStage().setUserId(userIds[i]).setStageId(stageId).setTime(time));
        if (!this.saveBatch(stages, n))
            return false;
        Integer taskId = userStageMapper.getOneTask(stageId);   // 获取阶段的第一个任务
        for (int i = 0; i < n; i++) {
            if (userStageMapper.updateUserStage(userIds[i], stageId, taskId))
                return false;    // 更新用户表
        }
        return true;
    }


    // 回退阶段任务
    @Override
    public boolean back(String[] userIds, Integer stageId, Integer newTaskId) {
        int n = userIds.length;
        for (int i = 0; i < n; i++) {
            userStageMapper.updateUserStage(userIds[i], stageId, newTaskId);
        }
        return false;
    }

    // 批量修改时间
    @Override
    public boolean updateTime(String[] userIds, String stageId, Date time) {
        for (String userId : userIds) {
            if (!userStageMapper.updateTime(userId, stageId, time))
                return false;
        }
        return true;
    }
}
