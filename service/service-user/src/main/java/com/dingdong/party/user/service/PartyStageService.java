package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyStage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.StageCountEntity;
import com.dingdong.party.user.entity.vo.StageEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyStageService extends IService<PartyStage> {

    /**
     * 获取所有入党阶段
     * @return
     */
    List<PartyStage> queryAllStageAndTask();

    StageEntity getStage(String id);

    /**
     * 删除
     * @param id
     */
    void remove(String id);

    /**
     * 更新
     * @param stage
     */
    void update(PartyStage stage);

    /**
     * 根据id查询入党阶段
     * @param id
     * @return
     */
    PartyStage queryById(String id);

    /**
     * 创建
     * @param stage
     */
    void create(PartyStage stage);

    /**
     * 获取最高期数
     * @return
     */
    Integer queryPeriodsNum();

    /**
     * 获取最高年级
     * @return
     */
    Integer queryGradeNum();

    /**
     * 修改最高期数
     * @param id
     * @param periodsNum
     * @return
     */
    void updatePeriodsNum(String id, Integer periodsNum);

    /**
     * 修改最高年级
     * @param id
     * @param gradeNum
     * @return
     */
    void updateGradeNum(String id, Integer gradeNum);

    /**
     * 统计各阶段人数
     * @return
     */
    List<StageCountEntity> queryStageCount();

}
