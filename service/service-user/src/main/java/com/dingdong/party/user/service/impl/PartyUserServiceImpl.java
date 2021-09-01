package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.user.entity.PartyStudent;
import com.dingdong.party.user.entity.PartyTeacher;
import com.dingdong.party.user.entity.PartyUser;
import com.dingdong.party.user.entity.vo.CommentEntity;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import com.dingdong.party.user.entity.vo.UserEntity;
import com.dingdong.party.user.mapper.PartyUserMapper;
import com.dingdong.party.user.service.PartyStudentService;
import com.dingdong.party.user.service.PartyTeacherService;
import com.dingdong.party.user.service.PartyUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class PartyUserServiceImpl extends ServiceImpl<PartyUserMapper, PartyUser> implements PartyUserService {

    @Resource
    PartyUserMapper userMapper;

    @Autowired
    PartyStudentService studentService;

    @Autowired
    PartyTeacherService teacherService;

    @Override
    public List<String> queryByBranchId(String branchId) {
        return userMapper.queryUserByBranchId(branchId);
    }

    @Override
    public List<String> queryUserByGroupId(String groupId) {
        return userMapper.queryUserByGroupId(groupId);
    }

    @Override
    public Map<String, Object> getList(String branchId, String groupId, Integer stageId, Integer periodsNum, String institute, String grade, String major, Integer page, Integer size) {
        QueryWrapper<PartyUser> wrapper = new QueryWrapper<>();
        if (branchId != null)
            wrapper.eq("branch_id", branchId);
        if (groupId != null)
            wrapper.eq("group_id", groupId);
        if (stageId != null)
            wrapper.eq("stage_id", stageId);
        if (periodsNum != null)
            wrapper.eq("periods_num", periodsNum);
        if (institute != null)
            wrapper.eq("institute", institute);
        if (grade != null)
            wrapper.eq("grade", grade);
        if (major != null)
            wrapper.eq("major", major);
        Page<PartyUser> userPage = new Page<>(page, size);
        this.page(userPage, wrapper);
        long total = userPage.getTotal();
        if (total == 0)
            return null;
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", userPage.getRecords());
        return map;
    }

    @Override
    public Object getActivities(String userId, Integer status, String activityId) {
        if (status != null)
            return userMapper.getUserActivity(userId, status);
        if (activityId != null)
            return userMapper.getOneActivity(userId, activityId);
        return userMapper.getUserAllActivity(userId);
    }

    @Override
    public List<CommentEntity> getComments(String userId) {
        return userMapper.getComments(userId);
    }

    @Override
    public PartyUser queryByOpenId(@PathVariable String openId) {
        QueryWrapper<PartyUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        return this.getOne(wrapper);
    }

    @Override
    public boolean updateTeacher(String userId, TeacherEntity teacherEntity) {
        try {
            PartyUser user = new PartyUser();
            BeanUtils.copyProperties(teacherEntity, user);
            user.setUserId(userId);
            this.updateById(user);
            PartyTeacher teacher = new PartyTeacher();
            BeanUtils.copyProperties(teacherEntity, teacher);
            teacherService.saveOrUpdate(teacher);
        } catch (Exception e) {
            throw new RuntimeException("修改信息异常");
        }
        return true;
    }

    @Override
    public boolean updateStudent(String userId, StudentEntity studentEntity) {
        try {
            PartyUser user = new PartyUser();
            BeanUtils.copyProperties(studentEntity, user);
            user.setUserId(userId);
            this.updateById(user);
            PartyStudent student = new PartyStudent();
            BeanUtils.copyProperties(studentEntity, student);
            studentService.saveOrUpdate(student);
        } catch (Exception e) {
            throw new RuntimeException("修改信息异常");
        }
        return true;
    }

    @Override
    public Map<String, Object> info(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        PartyUser user = this.getById(userId);
        map.put("main", user);
        if (user.getTeacherId() != null) {
            PartyTeacher teacher = teacherService.getById(user.getTeacherId());
            map.put("details", teacher);
        } else {
            PartyStudent student = studentService.getById(user.getStudentId());
            System.out.println(student);
            map.put("details", student);
        }
        return map;
    }
}
