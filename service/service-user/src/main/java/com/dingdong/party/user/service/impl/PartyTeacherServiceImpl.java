package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyTask;
import com.dingdong.party.user.entity.PartyTeacher;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import com.dingdong.party.user.mapper.PartyTeacherMapper;
import com.dingdong.party.user.service.PartyTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@Service
public class PartyTeacherServiceImpl extends ServiceImpl<PartyTeacherMapper, PartyTeacher> implements PartyTeacherService {

    @Override
    public List<TeacherEntity> getList(String name, String groupId, String groupName, String branchId, String branchName, String partyPosition, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyTeacher> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.like("name", name);
        }
        if (groupId != null) {
            wrapper.eq("group_id", groupId);
        }
        if (groupName != null) {
            wrapper.eq("group_name", groupName);
        }
        if (branchId != null) {
            wrapper.eq("branch_id", branchId);
        }
        if (branchName != null) {
            wrapper.eq("branch_name", branchName);
        }
        if (partyPosition != null) {
            wrapper.eq("party_position", partyPosition);
        }

        List<PartyTeacher> teacherList = this.list(wrapper);
        if (teacherList.size() <= 0) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return teacherList.stream().map(teacher -> {
            TeacherEntity teacherEntity = new TeacherEntity();
            BeanUtils.copyProperties(teacher, teacherEntity);
            return teacherEntity;
        }).collect(Collectors.toList());
    }

    @Override
    public TeacherEntity queryById(String id) {
        PartyTeacher teacher = this.getById(id);
        if (teacher == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        TeacherEntity teacherEntity = new TeacherEntity();
        BeanUtils.copyProperties(teacher, teacherEntity);
        return teacherEntity;
    }

    @Override
    public void create(TeacherEntity teacherEntity) {
        PartyTeacher teacher = new PartyTeacher();
        BeanUtils.copyProperties(teacherEntity, teacher);
        boolean res = this.save(teacher);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(String id, TeacherEntity teacherEntity) {
        PartyTeacher teacher = new PartyTeacher();
        BeanUtils.copyProperties(teacherEntity, teacher);
        teacher.setTeacherId(id);
        boolean res = this.updateById(teacher);
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
