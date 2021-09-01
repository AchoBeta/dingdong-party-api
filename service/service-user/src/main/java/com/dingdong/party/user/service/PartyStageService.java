package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyStage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.StageCountEntity;
import com.dingdong.party.user.entity.vo.StageEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface PartyStageService extends IService<PartyStage> {

    List<PartyStage> queryAllStageAndTask();

    StageEntity getStage(String id);

    Integer queryPeriodsNum();

    Integer queryGradeNum();

    List<StageCountEntity> queryStageCount();
}
