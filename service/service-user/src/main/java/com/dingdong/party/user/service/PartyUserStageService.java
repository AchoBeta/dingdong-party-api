package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyUserStage;
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
public interface PartyUserStageService extends IService<PartyUserStage> {

    Map<String, Object> getList(String userId, Integer stageId, Integer page, Integer size);
}
