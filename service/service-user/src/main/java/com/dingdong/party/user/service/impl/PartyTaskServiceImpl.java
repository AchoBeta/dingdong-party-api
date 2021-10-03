package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyTask;
import com.dingdong.party.user.entity.vo.TaskEntity;
import com.dingdong.party.user.mapper.PartyTaskMapper;
import com.dingdong.party.user.service.PartyTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@Service
public class PartyTaskServiceImpl extends ServiceImpl<PartyTaskMapper, PartyTask> implements PartyTaskService {

    @Override
    public List<TaskEntity> getList(String name, Integer stageId, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyTask> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.like("name", name);
        }
        if (stageId != null) {
            wrapper.eq("stage_id", stageId);
        }

        List<PartyTask> taskList = this.list(wrapper);
        if (taskList.size() <= 0) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return taskList.stream().map(task -> {
            TaskEntity taskEntity = new TaskEntity();
            BeanUtils.copyProperties(task, taskEntity);
            return taskEntity;
        }).collect(Collectors.toList());
    }

    @Override
    public PartyTask queryById(String id) {
        PartyTask task = this.getById(id);
        if (task == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return task;
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void create(PartyTask task) {
        boolean res = this.save(task);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(PartyTask task) {
        boolean res = this.updateById(task);
        if (!res) {
            throw new PartyException("修改失败", ResultCode.COMMON_FAIL.getCode());
        }
    }


}
