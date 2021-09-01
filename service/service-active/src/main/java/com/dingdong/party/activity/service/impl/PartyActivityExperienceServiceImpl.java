package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.activity.entity.PartyActivityExperience;
import com.dingdong.party.activity.mapper.PartyActivityExperienceMapper;
import com.dingdong.party.activity.service.PartyActivityExperienceService;
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
 * @since 2021-07-16
 */
@Service
public class PartyActivityExperienceServiceImpl extends ServiceImpl<PartyActivityExperienceMapper, PartyActivityExperience> implements PartyActivityExperienceService {

    @Override
    public Map<Object, Object> getList(String activityId, String userId, Integer page, Integer size) {
        QueryWrapper<PartyActivityExperience> wrapper = new QueryWrapper<>();
        if (activityId != null)
            wrapper.eq("activity_id", activityId);
        if (userId != null)
            wrapper.eq("user_id", userId);
        Page<PartyActivityExperience> experiencePage = new Page<>(page, size);
        this.page(experiencePage, wrapper);
        long total = experiencePage.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", experiencePage.getRecords());
        return map;
    }
}
