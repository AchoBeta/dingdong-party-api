package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyUserStage;
import com.dingdong.party.user.mapper.PartyUserStageMapper;
import com.dingdong.party.user.service.PartyUserStageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-08-12
 */
@Service
public class PartyUserStageServiceImpl extends ServiceImpl<PartyUserStageMapper, PartyUserStage> implements PartyUserStageService {

    @Override
    public List<PartyUserStage> getList(String userId, Integer stageId, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyUserStage> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (stageId != null) {
            wrapper.eq("stage_id", stageId);
        }

        return this.list(wrapper);
    }

    @Override
    public PartyUserStage queryById(String id) {
        PartyUserStage userStage = this.getById(id);
        if (userStage == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }

        return userStage;
    }

    @Override
    public void create(PartyUserStage userStage) {
        boolean res = this.save(userStage);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(PartyUserStage userStage, String id) {
        userStage.setId(id);
        boolean res = this.updateById(userStage);
        if (!res) {
            throw new PartyException("修改失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }
}
