package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyUserStage;
import com.dingdong.party.user.mapper.PartyUserStageMapper;
import com.dingdong.party.user.service.PartyUserStageService;
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
public class PartyUserStageServiceImpl extends ServiceImpl<PartyUserStageMapper, PartyUserStage> implements PartyUserStageService {

    @Override
    public Map<String, Object> getList(String userId, Integer stageId, Integer page, Integer size) {
        QueryWrapper<PartyUserStage> wrapper = new QueryWrapper<>();
        if (userId != null)
            wrapper.eq("user_id", userId);
        if (stageId != null)
            wrapper.eq("stage_id", stageId);
        Page<PartyUserStage> userStagePage = new Page<>(page, size);
        this.page(userStagePage, wrapper);
        long total = userStagePage.getTotal();
        if (total == 0)
            return null;
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", userStagePage.getRecords());
        return map;
    }
}
