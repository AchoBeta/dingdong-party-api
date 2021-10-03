package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyBranch;
import com.dingdong.party.user.mapper.PartyBranchMapper;
import com.dingdong.party.user.service.PartyBranchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class PartyBranchServiceImpl extends ServiceImpl<PartyBranchMapper, PartyBranch> implements PartyBranchService {

    @Override
    public List<PartyBranch> getList(String name, String parentId, String parentName, String directorId, String directorName, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyBranch> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.like("name", name);
        }
        if (parentId != null) {
            wrapper.eq("parent_id", parentId);
        }
        if (parentName != null) {
            wrapper.like("parent_name", parentName);
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
    public PartyBranch queryById(String id) {
        PartyBranch partyBranch = this.getById(id);
        if (partyBranch == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }

        return partyBranch;
    }

    @Override
    public void create(PartyBranch branch) {
        boolean res = this.save(branch);
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
    public void update(PartyBranch branch) {
        boolean res = this.updateById(branch);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }
}
