package com.dingdong.party.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.admin.entity.PartyFaculty;
import com.dingdong.party.admin.mapper.PartyFacultyMapper;
import com.dingdong.party.admin.service.PartyFacultyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.commonUtils.result.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    @Override
    public boolean create(PartyFaculty partyFaculty) {
        // 父节点不存在
        if (!("0".equals(partyFaculty.getPid())) && this.getById(partyFaculty.getPid()) == null) {
            throw new PartyException(ResultCode.PARAM_NOT_VALID);
        }

        return this.save(partyFaculty);
    }

    @Override
    public boolean remove(String id) {
        // 获取子树
        List<PartyFaculty> partyFacultyList = this.list();
        Queue<String> que = new LinkedList<>();
        que.add(id);
        List<String> idList = new ArrayList<>();
        while (!que.isEmpty()) {
            String cur = que.poll();
            idList.add(cur);
            for (PartyFaculty faculty : partyFacultyList) {
                if (faculty.getPid().equals(cur)) {
                    que.add(faculty.getId());
                }
            }
        }

        return this.removeByIds(idList);
    }

}
