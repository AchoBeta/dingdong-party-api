package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityComment;
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
public interface PartyActivityCommentService extends IService<PartyActivityComment> {

    Map<String, Object> getList(String activityId, Integer page, Integer size);
}
