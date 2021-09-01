package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityImage;
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
public interface PartyActivityImageService extends IService<PartyActivityImage> {

    Map<Object, Object> getList(String activityId, Integer page, Integer size);
}
