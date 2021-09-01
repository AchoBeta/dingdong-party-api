package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.activity.entity.PartyActivity;
import com.dingdong.party.activity.entity.PartyActivityDetails;
import com.dingdong.party.activity.entity.vo.ActivityDetailsEntity;
import com.dingdong.party.activity.mapper.PartyActivityMapper;
import com.dingdong.party.activity.service.PartyActivityDetailsService;
import com.dingdong.party.activity.service.PartyActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2021-07-16
 */
@Service
public class PartyActivityServiceImpl extends ServiceImpl<PartyActivityMapper, PartyActivity> implements PartyActivityService {

    @Autowired
    PartyActivityDetailsService detailsService;

    @Autowired
    PartyActivityMapper activityMapper;

    @Override
    public Map<Object, Object> getList(String name, String groupId, String branchId, String startTime, String endTime, String directorId, String directorName, Integer status, Integer page, Integer size) {
        QueryWrapper<PartyActivity> wrapper = new QueryWrapper<>();
        if (name != null)
            wrapper.like("name", name);
        if (groupId != null)
            wrapper.eq("group_id", groupId);
        if (branchId != null)
            wrapper.eq("branch_id", branchId);
        if (startTime != null)
            wrapper.ge("start_time", startTime);
        if (endTime != null)
            wrapper.le("end_time", endTime);
        if (startTime != null && endTime != null) {
            wrapper.ge("end_time", startTime);
            wrapper.le("start_time", endTime);
        }
        if (directorId != null)
            wrapper.eq("director_id", directorId);
        if (directorName != null)
            wrapper.like("director_name", directorName);
        if (status != null)
            wrapper.eq("status", status);
        Page<PartyActivity> activityPage = new Page<>(page, size);
        this.page(activityPage, wrapper);
        long total = activityPage.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", activityPage.getRecords());
        return map;
    }

    @Override
    public String create(ActivityDetailsEntity activityDetailsEntity) throws Exception {
        PartyActivity activity = new PartyActivity();
        BeanUtils.copyProperties(activityDetailsEntity, activity);
        System.out.println(activity);
        if (!this.save(activity))
            throw new Exception("创建活动异常");
        PartyActivityDetails details = new PartyActivityDetails();
        BeanUtils.copyProperties(activityDetailsEntity, details);
        details.setId(activity.getId());
        if (!detailsService.save(details))
            throw new Exception("创建活动细节异常");
        return activity.getId();
    }

    /**
     * 删除活动
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        try {
            this.removeById(id);
            detailsService.removeById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("删除失败");
        }
    }

    /**
     * 修改活动
     * @param detailsEntity
     * @return
     */
    @Override
    public boolean modify(ActivityDetailsEntity detailsEntity) {
        PartyActivity activity = new PartyActivity();
        BeanUtils.copyProperties(detailsEntity, activity);
        if (!this.updateById(activity))
            throw new RuntimeException("修改失败");
        PartyActivityDetails details = new PartyActivityDetails();
        BeanUtils.copyProperties(detailsEntity, details);
        if (!detailsService.updateById(details))
            throw new RuntimeException("需改失败");
        return true;
    }

    @Override
    public boolean commit(String activityId) {
        return activityMapper.commit(activityId);
    }

    // 小程序端查看所有活动
    @Override
    public Map<String, Object> queryAll(Integer page, Integer size) {
        QueryWrapper<PartyActivity> wrapper = new QueryWrapper<>();
        wrapper.ge("status", 3);
        wrapper.orderByAsc("status");
        Page<PartyActivity> activityPage = new Page<>(page, size);
        this.page(activityPage, wrapper);
        long total = activityPage.getTotal();
        if (total == 0)
            return null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", activityPage.getRecords());
        return map;
    }
}
