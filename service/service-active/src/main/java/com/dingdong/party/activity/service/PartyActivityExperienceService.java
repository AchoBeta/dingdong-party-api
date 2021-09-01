package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityExperience;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
public interface PartyActivityExperienceService extends IService<PartyActivityExperience> {

    Map<Object, Object> getList(String activityId, String userId, Integer page, Integer size);
}
