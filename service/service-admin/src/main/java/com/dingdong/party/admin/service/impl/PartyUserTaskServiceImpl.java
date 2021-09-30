package com.dingdong.party.admin.service.impl;

import com.dingdong.party.admin.entity.PartyUserStage;
import com.dingdong.party.admin.entity.PartyUserTask;
import com.dingdong.party.admin.mapper.PartyUserTaskMapper;
import com.dingdong.party.admin.service.PartyUserTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
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
public class PartyUserTaskServiceImpl extends ServiceImpl<PartyUserTaskMapper, PartyUserTask> implements PartyUserTaskService {

    @Resource
    PartyUserTaskMapper taskMapper;

    @Override
    public boolean changeUserTask(String[] userIds, Integer taskId, Date time) {
        int n = userIds.length;
        List<PartyUserTask> tasks = new ArrayList<>();
        for (int i = 0; i < n; i++)
            tasks.add(new PartyUserTask().setUserId(userIds[i]).setTaskId(taskId).setTime(time));
        if (!this.saveBatch(tasks, n))
            return false;
        for (int i = 0; i < n; i++) {
            if (taskMapper.updateUserTask(userIds[i], taskId))
                return false;    // 更新用户表
        }
        return true;
    }

    @Override
    public boolean updateTime(String[] userIds, String taskId, Date time) {
        for (String userId : userIds) {
            if (!taskMapper.updateTime(userId, taskId, time))
                return false;
        }
        return true;
    }
}
