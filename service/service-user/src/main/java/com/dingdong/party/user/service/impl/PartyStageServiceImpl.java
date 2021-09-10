package com.dingdong.party.user.service.impl;

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
 * @author testjava
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
        return stageMapper.selectList(null);
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

    // 获取当前最高的期数
    @Override
    public Integer queryPeriodsNum() {
        return stageMapper.queryNum("12462534234");
    }

    // 获取当前最高的年级
    @Override
    public Integer queryGradeNum() {
        return stageMapper.queryNum("14192307563");
    }

    @Override
    public Boolean updatePeriodsNum(String id, Integer periodsNum) {
        return stageMapper.updateNum(id, periodsNum);
    }

    @Override
    public Boolean updateGradeNum(String id, Integer gradeNum) {
        return stageMapper.updateNum(id, gradeNum);
    }

    // 获取数据
    @Override
    public List<StageCountEntity> queryStageCount() {
        System.out.println(1);
        return stageMapper.queryStageCount().parallelStream()
                .peek(x -> x.setOrder(x.getId()))
                .collect(Collectors.toList());
    }

}
