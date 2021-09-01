package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyTask;
import com.dingdong.party.user.entity.PartyTeacher;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import com.dingdong.party.user.mapper.PartyTeacherMapper;
import com.dingdong.party.user.service.PartyTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class PartyTeacherServiceImpl extends ServiceImpl<PartyTeacherMapper, PartyTeacher> implements PartyTeacherService {

    @Override
    public Map<Object, Object> getList(String name, String groupId, String groupName, String branchId, String branchName, String partyPosition, Integer page, Integer size) {
        QueryWrapper<PartyTeacher> wrapper = new QueryWrapper<>();
        if (name != null)
            wrapper.like("name", name);
        if (groupId != null)
            wrapper.eq("group_id", groupId);
        if (groupName != null)
            wrapper.eq("group_name", groupName);
        if (branchId != null)
            wrapper.eq("branch_id", branchId);
        if (branchName != null)
            wrapper.eq("branch_name", branchName);
        if (partyPosition != null)
            wrapper.eq("party_position", partyPosition);
        Page<PartyTeacher> partyTeacherPage = new Page<>(page, size);
        this.page(partyTeacherPage, wrapper);
        long total = partyTeacherPage.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        ArrayList<TeacherEntity> list = new ArrayList<>();
        for (PartyTeacher teacher : partyTeacherPage.getRecords()) {
            TeacherEntity teacherEntity = new TeacherEntity();
            BeanUtils.copyProperties(teacher, teacherEntity);
            list.add(teacherEntity);
        }
        map.put("items", list);
        return map;
    }
}
