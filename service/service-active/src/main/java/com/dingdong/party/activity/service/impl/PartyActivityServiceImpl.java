package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.activity.entity.PartyActivity;
import com.dingdong.party.activity.entity.PartyActivityDetails;
import com.dingdong.party.activity.entity.vo.ActivityDetailsEntity;
import com.dingdong.party.activity.mapper.PartyActivityMapper;
import com.dingdong.party.activity.service.PartyActivityDetailsService;
import com.dingdong.party.activity.service.PartyActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
@Service
public class PartyActivityServiceImpl extends ServiceImpl<PartyActivityMapper, PartyActivity> implements PartyActivityService {

    @Resource
    PartyActivityDetailsService detailsService;

    @Resource
    PartyActivityMapper activityMapper;

    @Override
    public List<PartyActivity> getList(String name, String branchId, String startTime, String endTime, String directorId, String directorName, Integer status, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyActivity> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.like("name", name);
        }
        if (branchId != null) {
            wrapper.eq("branch_id", branchId);
        }
        if (startTime != null) {
            wrapper.ge("start_time", startTime);
        }
        if (endTime != null) {
            wrapper.le("end_time", endTime);
        }
        if (startTime != null && endTime != null) {
            wrapper.ge("end_time", startTime);
            wrapper.le("start_time", endTime);
        }
        if (directorId != null) {
            wrapper.eq("director_id", directorId);
        }
        if (directorName != null) {
            wrapper.like("director_name", directorName);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }

        List<PartyActivity> list = this.list(wrapper);
        if (list.size() <= 0) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return list;
    }

    @Override
    public void create(ActivityDetailsEntity activityDetailsEntity) {
        PartyActivity activity = new PartyActivity();
        BeanUtils.copyProperties(activityDetailsEntity, activity);
        if (!this.save(activity)) {
            throw new PartyException("创建活动异常", ResultCode.COMMON_FAIL.getCode());
        }

        PartyActivityDetails details = new PartyActivityDetails();
        BeanUtils.copyProperties(activityDetailsEntity, details);
        details.setId(activity.getId());
        if (!detailsService.save(details)) {
            throw new PartyException("创建活动细节异常", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        try {
            this.removeById(id);
            detailsService.removeById(id);
        } catch (Exception e) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(String id, ActivityDetailsEntity detailsEntity) {
        PartyActivity activity = new PartyActivity();
        BeanUtils.copyProperties(detailsEntity, activity);
        if (!this.updateById(activity)) {
            throw new PartyException("修改活动异常", ResultCode.COMMON_FAIL.getCode());
        }

        PartyActivityDetails details = new PartyActivityDetails();
        BeanUtils.copyProperties(detailsEntity, details);
        if (!detailsService.updateById(details)) {
            throw new PartyException("修改活动细节异常", ResultCode.COMMON_FAIL.getCode());
        }
    }


    @Override
    public void commit(String activityId) {
        boolean res = activityMapper.commit(activityId);
        if (!res) {
            throw new PartyException("提交失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    /**
     * 小程序端查看所有活动
     */
    @Override
    public List<PartyActivity> queryAll(Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyActivity> wrapper = new QueryWrapper<>();
        wrapper.ge("status", 3);
        wrapper.orderByAsc("status");

        return this.list(wrapper);
    }

    @Override
    public ActivityDetailsEntity queryById(String id) {
        PartyActivity activity = this.getById(id);
        PartyActivityDetails details = detailsService.getById(id);

        if (activity == null || details == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }

        ActivityDetailsEntity detailsEntity = new ActivityDetailsEntity();
        BeanUtils.copyProperties(activity, detailsEntity);
        BeanUtils.copyProperties(details, detailsEntity);
        return detailsEntity;
    }
}
