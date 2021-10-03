package com.dingdong.party.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.dingdong.party.user.entity.PartyStudent;
import com.dingdong.party.user.entity.PartyTeacher;
import com.dingdong.party.user.entity.PartyUser;
import com.dingdong.party.user.entity.vo.*;
import com.dingdong.party.user.mapper.PartyUserMapper;
import com.dingdong.party.user.service.PartyStudentService;
import com.dingdong.party.user.service.PartyTeacherService;
import com.dingdong.party.user.service.PartyUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@Service
public class PartyUserServiceImpl extends ServiceImpl<PartyUserMapper, PartyUser> implements PartyUserService {

    @Resource
    PartyUserMapper userMapper;

    @Resource
    PartyStudentService studentService;

    @Resource
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
    public List<PartyUser> getList(String branchId, String groupId, Integer stageId, Integer periodsNum, String institute, String grade, String major, Integer page, Integer size) {
        PageHelper.startPage(page, size);

        QueryWrapper<PartyUser> wrapper = new QueryWrapper<>();
        if (branchId != null) {
            wrapper.eq("branch_id", branchId);
        }
        if (groupId != null) {
            wrapper.eq("group_id", groupId);
        }
        if (stageId != null) {
            wrapper.eq("stage_id", stageId);
        }
        if (periodsNum != null) {
            wrapper.eq("periods_num", periodsNum);
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

        return this.list(wrapper);
    }

    @Override
    public List<UserActivityEntity> getActivities(String userId, Integer status, String activityId) {
        List<UserActivityEntity> res = null;

        if (status != null) {
            res = userMapper.getUserActivity(userId, status);
        } else if (activityId != null) {
            res = userMapper.getOneActivity(userId, activityId);
        } else {
            res = userMapper.getUserAllActivity(userId);
        }

        return res;
    }

    @Override
    public List<CommentEntity> getComments(String userId) {
        return userMapper.getComments(userId);
    }

    @Override
    public void create(PartyUser user) {
        if (!this.save(user)) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        if (!this.removeById(id)) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public PartyUser queryByOpenId(@PathVariable String openId) {
        QueryWrapper<PartyUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        return this.getOne(wrapper);
    }

    @Override
    public void updateTeacher(String userId, TeacherEntity teacherEntity) {
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
    }

    @Override
    public void updateStudent(String userId, StudentEntity studentEntity) {
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
    }

    @Override
    public UserInfoVO info(String userId) {
        PartyUser user = this.getById(userId);
        UserRole userRole = null;

        if (user.getTeacherId() != null) {
            userRole = teacherService.getById(user.getTeacherId());
        } else {
            userRole = studentService.getById(user.getStudentId());
        }

        if (user == null || userRole == null) {
            throw new PartyException("查询失败", ResultCode.COMMON_FAIL.getCode());
        }

        return new UserInfoVO(user, userRole);
    }
}
