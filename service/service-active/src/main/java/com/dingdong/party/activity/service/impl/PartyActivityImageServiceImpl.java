package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.activity.entity.PartyActivityImage;
import com.dingdong.party.activity.mapper.PartyActivityImageMapper;
import com.dingdong.party.activity.service.PartyActivityImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.management.Query;
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
public class PartyActivityImageServiceImpl extends ServiceImpl<PartyActivityImageMapper, PartyActivityImage> implements PartyActivityImageService {

    @Override
    public Map<Object, Object> getList(String activityId, Integer page, Integer size) {
        QueryWrapper<PartyActivityImage> wrapper = new QueryWrapper<>();
        if (activityId != null) {
            wrapper.eq("activity_id", activityId);
        }
        Page<PartyActivityImage> imagePage = new Page<>(page, size);
        this.page(imagePage, wrapper);
        long total = imagePage.getTotal();
        if (total == 0) {
            return null;
        }
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", imagePage.getRecords());
        return map;
    }
}
