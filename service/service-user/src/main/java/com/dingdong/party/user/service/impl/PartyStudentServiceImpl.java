package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyStudent;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.mapper.PartyStudentMapper;
import com.dingdong.party.user.service.PartyStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class PartyStudentServiceImpl extends ServiceImpl<PartyStudentMapper, PartyStudent> implements PartyStudentService {

    @Override
    public List<StudentEntity> getList(String branchId, String groupId, String institute, String grade, String major, String className, String dormitoryArea, int page, int size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyStudent> wrapper = new QueryWrapper<>();
        if (branchId != null) {
            wrapper.eq("branch_id", branchId);
        }
        if (groupId != null) {
            wrapper.eq("group_id", groupId);
        }
        if (institute != null) {
            wrapper.eq("institute", institute);
        }
        if (grade != null) {
            wrapper.eq("grade", grade);
        }
        if (major != null) {
            wrapper.eq("major", major);
        }
        if (className != null) {
            wrapper.eq("class_name", className);
        }
        if (dormitoryArea != null) {
            wrapper.eq("dormitory_area", dormitoryArea);
        }

        List<PartyStudent> res = this.list(wrapper);
        if (res.size() <= 0) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return res.stream().map(student -> {
            StudentEntity studentEntity = new StudentEntity();
            BeanUtils.copyProperties(student, studentEntity);
            return studentEntity;
        }).collect(Collectors.toList());
    }

    @Override
    public StudentEntity queryStudentById(String studentNo) {
        PartyStudent student = this.getById(studentNo);
        if (student == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }
        StudentEntity studentEntity = new StudentEntity();
        BeanUtils.copyProperties(student, studentEntity);

        return studentEntity;
    }

    @Override
    public void create(StudentEntity studentEntity) {
        PartyStudent student = new PartyStudent();
        BeanUtils.copyProperties(studentEntity, student);

        boolean res = this.save(student);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(String studentId, StudentEntity studentEntity) {
        PartyStudent student = new PartyStudent();
        BeanUtils.copyProperties(studentEntity, student);
        student.setStudentId(studentId);
        boolean res = this.updateById(student);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
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
