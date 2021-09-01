package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyBranch;
import com.dingdong.party.user.mapper.PartyBranchMapper;
import com.dingdong.party.user.service.PartyBranchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class PartyBranchServiceImpl extends ServiceImpl<PartyBranchMapper, PartyBranch> implements PartyBranchService {

    @Override
    public Map<Object, Object> getList(String name, String parentId, String parentName, String directorId, String directorName, Integer page, Integer size) {
        QueryWrapper<PartyBranch> wrapper = new QueryWrapper<>();
        if (name != null)
            wrapper.like("name", name);
        if (parentId != null)
            wrapper.eq("parent_id", parentId);
        if (parentName != null)
            wrapper.like("parent_name", parentName);
        if (directorId != null)
            wrapper.eq("director_id", directorId);
        if (directorName != null)
            wrapper.like("director_name", directorName);
        Page<PartyBranch> pageBranch = new Page<>();
        this.page(pageBranch, wrapper);
        long total = pageBranch.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", pageBranch.getRecords());
        return map;
    }
}
