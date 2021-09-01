package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyTask;
import com.dingdong.party.user.entity.vo.TaskEntity;
import com.dingdong.party.user.mapper.PartyTaskMapper;
import com.dingdong.party.user.service.PartyTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class PartyTaskServiceImpl extends ServiceImpl<PartyTaskMapper, PartyTask> implements PartyTaskService {

    @Override
    public Map<Object, Object> getList(String name, Integer stageId, Integer page, Integer size) {
        QueryWrapper<PartyTask> wrapper = new QueryWrapper<>();
        if (name != null)
            wrapper.like("name", name);
        if (stageId != null)
            wrapper.eq("stage_id", stageId);
        Page<PartyTask> partyTaskPage = new Page<>(page, size);
        this.page(partyTaskPage, wrapper);
        long total = partyTaskPage.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        ArrayList<TaskEntity> list = new ArrayList<>();
        for (PartyTask task : partyTaskPage.getRecords()) {
            TaskEntity taskEntity = new TaskEntity();
            BeanUtils.copyProperties(task, taskEntity);
            list.add(taskEntity);
        }
        map.put("items", list);
        return map;
    }
}
