package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyGroup;
import com.dingdong.party.user.mapper.PartyGroupMapper;
import com.dingdong.party.user.service.PartyGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class PartyGroupServiceImpl extends ServiceImpl<PartyGroupMapper, PartyGroup> implements PartyGroupService {

    @Override
    public Map<Object, Object> getList(String name, String branchId, String branchName, String directorId, String directorName, Integer page, Integer size) {
        QueryWrapper<PartyGroup> wrapper = new QueryWrapper<>();
        if (name != null)
            wrapper.like("name", name);
        if (branchId != null)
            wrapper.eq("branch_id", branchId);
        if (branchName != null)
            wrapper.like("branch_name", branchName);
        if (directorId != null)
            wrapper.eq("director_id", directorId);
        if (directorName != null)
            wrapper.like("director_name", directorName);
        Page<PartyGroup> pageGroup = new Page<>();
        this.page(pageGroup, wrapper);
        long total = pageGroup.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", pageGroup.getRecords());
        return map;
    }
}
