package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
public interface PartyActivityDetailsService extends IService<PartyActivityDetails> {

    /**
     * 查询
     *
     * @param activityId
     * @return
     */
    PartyActivityDetails queryByActivityId(String activityId);

}
