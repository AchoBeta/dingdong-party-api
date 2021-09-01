package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyUserTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-12
 */
public interface PartyUserTaskService extends IService<PartyUserTask> {

    Map<String, Object> getList(String userId, Integer taskId, Integer page, Integer size);
}
