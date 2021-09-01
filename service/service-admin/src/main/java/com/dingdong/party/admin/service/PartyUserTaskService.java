package com.dingdong.party.admin.service;

import com.dingdong.party.admin.entity.PartyUserTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
public interface PartyUserTaskService extends IService<PartyUserTask> {

    boolean changeUserTask(String[] userIds, Integer taskId, Date time);

    boolean updateTime(String[] userIds, String taskId, Date time);
}
