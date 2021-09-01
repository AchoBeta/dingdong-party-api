package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyUserStage;
import com.dingdong.party.user.entity.PartyUserTask;
import com.dingdong.party.user.mapper.PartyUserTaskMapper;
import com.dingdong.party.user.service.PartyUserTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-12
 */
@Service
public class PartyUserTaskServiceImpl extends ServiceImpl<PartyUserTaskMapper, PartyUserTask> implements PartyUserTaskService {

    @Override
    public Map<String, Object> getList(String userId, Integer taskId, Integer page, Integer size) {
            QueryWrapper<PartyUserTask> wrapper = new QueryWrapper<>();
            if (userId != null)
                wrapper.eq("user_id", userId);
            if (taskId != null)
                wrapper.eq("task_id", taskId);
            Page<PartyUserTask> userTaskPage = new Page<>(page, size);
            this.page(userTaskPage, wrapper);
            long total = userTaskPage.getTotal();
            if (total == 0)
                return null;
            HashMap<String, Object> map = new HashMap<>(2);
            map.put("total", total);
            map.put("items", userTaskPage.getRecords());
            return map;
    }
}
