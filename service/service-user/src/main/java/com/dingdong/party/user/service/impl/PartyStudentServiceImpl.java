package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyStudent;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.mapper.PartyStudentMapper;
import com.dingdong.party.user.service.PartyStudentService;
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
public class PartyStudentServiceImpl extends ServiceImpl<PartyStudentMapper, PartyStudent> implements PartyStudentService {

    @Override
    public Map<Object, Object> getList(String branchId, String groupId, String institute, String grade, String major, String className, String dormitoryArea, int page, int size) {
        QueryWrapper<PartyStudent> wrapper = new QueryWrapper<>();
        if (branchId != null)
            wrapper.eq("branch_id", branchId);
        if (groupId != null)
            wrapper.eq("group_id", groupId);
        if (institute != null)
            wrapper.eq("institute", institute);
        if (grade != null)
            wrapper.eq("grade", grade);
        if (major != null)
            wrapper.eq("major", major);
        if (className != null)
            wrapper.eq("class_name", className);
        if (dormitoryArea != null);
            wrapper.eq("dormitory_area", dormitoryArea);
        Page<PartyStudent> pageStudent = new Page<>(page, size);
        this.page(pageStudent, wrapper);
        long total = pageStudent.getTotal();
        if (total == 0)
            return null;
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("total", total);
        ArrayList<StudentEntity> list = new ArrayList<>();
        for (PartyStudent student : pageStudent.getRecords()) {
            StudentEntity studentEntity = new StudentEntity();
            BeanUtils.copyProperties(student, studentEntity);
            list.add(studentEntity);
        }
        map.put("items", list);
        return map;
    }
}
