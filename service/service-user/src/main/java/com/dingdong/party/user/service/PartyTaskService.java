package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface PartyTaskService extends IService<PartyTask> {

    Map<Object, Object> getList(String name, Integer stageId, Integer page, Integer size);
}
