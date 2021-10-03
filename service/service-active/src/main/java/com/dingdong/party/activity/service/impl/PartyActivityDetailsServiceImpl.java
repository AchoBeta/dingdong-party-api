package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.activity.entity.PartyActivityDetails;
import com.dingdong.party.activity.mapper.PartyActivityDetailsMapper;
import com.dingdong.party.activity.service.PartyActivityDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
@Service
public class PartyActivityDetailsServiceImpl extends ServiceImpl<PartyActivityDetailsMapper, PartyActivityDetails> implements PartyActivityDetailsService {

    @Override
    public PartyActivityDetails queryByActivityId(String activityId) {
        PartyActivityDetails details = this.getById(activityId);
        if (details == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return details;
    }
}
