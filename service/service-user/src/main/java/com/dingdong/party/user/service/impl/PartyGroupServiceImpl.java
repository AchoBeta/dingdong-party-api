package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyGroup;
import com.dingdong.party.user.mapper.PartyGroupMapper;
import com.dingdong.party.user.service.PartyGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@Service
public class PartyGroupServiceImpl extends ServiceImpl<PartyGroupMapper, PartyGroup> implements PartyGroupService {

    @Override
    public List<PartyGroup> getList(String name, String branchId, String branchName, String directorId, String directorName, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyGroup> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.like("name", name);
        }
        if (branchId != null) {
            wrapper.eq("branch_id", branchId);
        }
        if (branchName != null) {
            wrapper.like("branch_name", branchName);
        }
        if (directorId != null) {
            wrapper.eq("director_id", directorId);
        }
        if (directorName != null) {
            wrapper.like("director_name", directorName);
        }

        return this.list(wrapper);
    }

    @Override
    public PartyGroup queryById(String id) {
        PartyGroup group = this.getById(id);
        if (group == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }

        return group;
    }

    @Override
    public void create(PartyGroup group) {
        boolean res = this.save(group);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(PartyGroup group) {
        boolean res = this.updateById(group);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }
}
