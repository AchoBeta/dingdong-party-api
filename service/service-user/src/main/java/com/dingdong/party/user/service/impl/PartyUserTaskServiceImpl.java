package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyUserTask;
import com.dingdong.party.user.mapper.PartyUserTaskMapper;
import com.dingdong.party.user.service.PartyUserTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-08-12
 */
@Service
public class PartyUserTaskServiceImpl extends ServiceImpl<PartyUserTaskMapper, PartyUserTask> implements PartyUserTaskService {

    @Override
    public List<PartyUserTask> getList(String userId, Integer taskId, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyUserTask> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (taskId != null) {
            wrapper.eq("task_id", taskId);
        }

        return this.list(wrapper);
    }

    @Override
    public PartyUserTask queryById(String id) {
        PartyUserTask task = this.getById(id);
        if (task == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return task;
    }

    @Override
    public void create(PartyUserTask userTask) {
        boolean res = this.save(userTask);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(PartyUserTask userTask, String id) {
        userTask.setId(id);
        boolean res = this.updateById(userTask);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }
}
