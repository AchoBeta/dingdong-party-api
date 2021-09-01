package com.dingdong.party.admin.service;

import com.dingdong.party.admin.entity.PartyUserStage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
public interface PartyUserStageService extends IService<PartyUserStage> {

    boolean changeUserStage(String[] userIds, Integer stageId, Date time);

    boolean back(String[] userIds, Integer stageId, Integer newTaskId);

    boolean updateTime(String[] userIds, String stageId, Date time);
}
