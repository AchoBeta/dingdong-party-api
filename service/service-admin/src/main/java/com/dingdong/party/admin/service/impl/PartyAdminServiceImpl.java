package com.dingdong.party.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.admin.entity.PartyAdmin;
import com.dingdong.party.admin.entity.vo.ActivityTimeEntity;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.admin.entity.vo.UserAdminEntity;
import com.dingdong.party.admin.mapper.PartyAdminMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.admin.service.PartyAdminService;
import com.dingdong.party.commonUtils.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class PartyAdminServiceImpl extends ServiceImpl<PartyAdminMapper, PartyAdmin> implements PartyAdminService {

    @Autowired
    PartyAdminMapper adminMapper;

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);  // 创建定时器线程，线程数为当前核心数的两倍

    @Override
    public PartyAdmin queryByUsername(String username) {
        QueryWrapper<PartyAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return this.getOne(wrapper);
    }

    @Override
    public boolean updateUserById(String userId, Integer status, String statusReason) {
        return adminMapper.updateUserById(userId, status, statusReason);
    }

    @Override
    public Object info(String userId) {
        HashMap<String, String> map = adminMapper.judgeTeacherStudent(userId);
        if (map.get("studentId") != null) {
            return adminMapper.getStudent(map.get("studentId"));
        }
        return adminMapper.getTeacher(map.get("teacherId"));
    }

    @Override
    public boolean examineActivity(String activityId) {
        // 获取活动开始时间和截至时间
        ActivityTimeEntity timeEntity = adminMapper.getActivityTime(activityId);
        Date now = new Date();
        Date startTime = timeEntity.getStartTime();
        Date endTime = timeEntity.getEndTime();
        Long start = (startTime.getTime() - now.getTime()) / 1000;
        // 开启活动开始的定时任务
        scheduledThreadPool.schedule(() -> {
                adminMapper.examineActivity(activityId, 4);
        }, start, TimeUnit.SECONDS);

        // 开启活动结束的定时任务
        Long end = (endTime.getTime() - now.getTime()) / 1000;
        scheduledThreadPool.schedule(() -> {
            adminMapper.examineActivity(activityId, 5);
        }, end, TimeUnit.SECONDS);

        return adminMapper.examineActivity(activityId, 3);
    }

    @Override
    public boolean rejectActivity(String activityId) {
        return adminMapper.rejectActivity(activityId);
    }

    // 活动请假成功
    @Override
    public boolean partakeActivitySuccess(String userId, String activityId) {
        return adminMapper.examineUserActivity(userId, activityId, 2) && adminMapper.changeActivityNum(activityId, -1);
    }

    // 活动请假失败
    @Override
    public boolean partakeActivityFail(String userId, String activityId) {
        return adminMapper.examineUserActivity(userId, activityId, 3);
    }

    // 参与活动成功
    @Override
    public boolean participateActivitySuccess(String userId, String activityId) {
        return adminMapper.examineUserActivity(userId, activityId, 0) && adminMapper.changeActivityNum(activityId, 1);
    }

    // 参与活动失败
    @Override
    public boolean participateActivityFail(String userId, String activityId) {
        return adminMapper.examineUserActivity(userId, activityId, 5);
    }

    @Override
    public boolean changePassword(String adminId, String oldPwd, String newPwd) {
        PartyAdmin admin = this.getById(adminId);
        oldPwd = MD5.encrypt(oldPwd);
        if (admin.getPassword().equals(oldPwd)) {
            newPwd = MD5.encrypt(newPwd);
            admin.setPassword(newPwd);
            this.updateById(admin);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(PartyAdmin admin) {
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        return this.save(admin);
    }

    @Override
    public Map<String, Object> getList(String groupId, String branchId, Integer authority, Integer page, Integer size) {
        QueryWrapper<PartyAdmin> wrapper = new QueryWrapper<>();
        if (groupId != null)
            wrapper.eq("group_id", groupId);
        if (branchId != null)
            wrapper.eq("branch_id", branchId);
        if (authority != null)
            wrapper.eq("authority", authority);
        Page<PartyAdmin> adminPage = new Page<>(page, size);
        this.page(adminPage, wrapper);
        long total = adminPage.getTotal();
        if (total == 0)
            return null;
        List<UserAdminEntity> list = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            PartyAdmin admin = adminPage.getRecords().get(i);
            UserAdminEntity adminEntity = adminMapper.queryInfo(admin.getUserId());
            BeanUtils.copyProperties(adminPage.getRecords().get(i), adminEntity);
            list.add(adminEntity);
        }
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("items", list);
        return map;
    }

    // 根据用户id获取真实姓名
    @Override
    public AdminEntity queryNameByUserId(String userId) {
        return adminMapper.queryInfoByUserId(userId);
    }
}
