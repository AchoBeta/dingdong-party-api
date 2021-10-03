package com.dingdong.party.user.service.impl;

import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyStage;
import com.dingdong.party.user.entity.vo.StageCountEntity;
import com.dingdong.party.user.entity.vo.StageEntity;
import com.dingdong.party.user.entity.vo.TaskEntity;
import com.dingdong.party.user.mapper.PartyStageMapper;
import com.dingdong.party.user.mapper.PartyTaskMapper;
import com.dingdong.party.user.service.PartyStageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@Service
public class PartyStageServiceImpl extends ServiceImpl<PartyStageMapper, PartyStage> implements PartyStageService {

    @Resource
    PartyStageMapper stageMapper;

    @Resource
    PartyTaskMapper taskMapper;

    @Override
    public List<PartyStage> queryAllStageAndTask() {
        List<PartyStage> list = stageMapper.selectList(null);
        if (list == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }
        return list;
    }

    @Override
    public StageEntity getStage(String id) {
        PartyStage stage = this.getById(id);
        List<TaskEntity> tasks = taskMapper.select(id);
        StageEntity stageEntity = new StageEntity();
        BeanUtils.copyProperties(stage, stageEntity);
        stageEntity.setTaskEntities(tasks);
        return stageEntity;
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(PartyStage stage) {
        boolean res = this.updateById(stage);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public PartyStage queryById(String id) {
        PartyStage stage = this.getById(id);
        if (stage == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }

        return stage;
    }

    @Override
    public void create(PartyStage stage) {
        boolean res = this.save(stage);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public Integer queryPeriodsNum() {
        return stageMapper.queryNum("12462534234");
    }

    @Override
    public Integer queryGradeNum() {
        return stageMapper.queryNum("14192307563");
    }

    @Override
    public void updatePeriodsNum(String id, Integer periodsNum) {
        Boolean res = stageMapper.updateNum(id, periodsNum);
        if (!res) {
            throw new PartyException("修改失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void updateGradeNum(String id, Integer gradeNum) {
        Boolean res = stageMapper.updateNum(id, gradeNum);
        if (!res) {
            throw new PartyException("修改失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public List<StageCountEntity> queryStageCount() {
        System.out.println(1);
        return stageMapper.queryStageCount().parallelStream()
                .peek(x -> x.setOrder(x.getId()))
                .collect(Collectors.toList());
    }

}
