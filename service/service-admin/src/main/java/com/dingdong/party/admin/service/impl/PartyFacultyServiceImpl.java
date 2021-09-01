package com.dingdong.party.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.admin.entity.PartyFaculty;
import com.dingdong.party.admin.mapper.PartyFacultyMapper;
import com.dingdong.party.admin.service.PartyFacultyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-08
 */
@Service
public class PartyFacultyServiceImpl extends ServiceImpl<PartyFacultyMapper, PartyFaculty> implements PartyFacultyService {

    @Override
    public List<PartyFaculty> query(String pid) {
        QueryWrapper<PartyFaculty> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        return this.list(wrapper);
    }
}
