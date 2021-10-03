package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.TaskEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyTaskService extends IService<PartyTask> {

    /**
     * 分页
     *
     * @param name
     * @param stageId
     * @param page
     * @param size
     * @return
     */
    List<TaskEntity> getList(String name, Integer stageId, Integer page, Integer size);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    PartyTask queryById(String id);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);

    /**
     * 创建
     *
     * @param task
     */
    void create(PartyTask task);

    /**
     * 更新
     *
     * @param task
     */
    void update(PartyTask task);

}
