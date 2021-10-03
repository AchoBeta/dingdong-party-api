package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.activity.entity.PartyActivityImage;
import com.dingdong.party.activity.mapper.PartyActivityImageMapper;
import com.dingdong.party.activity.service.PartyActivityImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
@Service
public class PartyActivityImageServiceImpl extends ServiceImpl<PartyActivityImageMapper, PartyActivityImage> implements PartyActivityImageService {

    @Override
    public List<PartyActivityImage> getList(String activityId, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyActivityImage> wrapper = new QueryWrapper<>();
        if (activityId != null) {
            wrapper.eq("activity_id", activityId);
        }

        List<PartyActivityImage> list = this.list(wrapper);
        if (list.size() <= 0) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return list;
    }

    @Override
    public PartyActivityImage queryById(String id) {
        PartyActivityImage image = this.getById(id);
        if (image == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return image;
    }

    @Override
    public void create(String activityId, PartyActivityImage image) {
        image.setActivityId(activityId);

        boolean res = this.save(image);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(String activityId, String id, PartyActivityImage image) {
        image.setId(id);
        image.setActivityId(activityId);

        boolean res = this.updateById(image);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }
}
