package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyUserTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author retraci
 * @since 2021-08-12
 */
public interface PartyUserTaskService extends IService<PartyUserTask> {

    /**
     * 分页
     *
     * @param userId
     * @param taskId
     * @param page
     * @param size
     * @return
     */
    List<PartyUserTask> getList(String userId, Integer taskId, Integer page, Integer size);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    PartyUserTask queryById(String id);

    /**
     * 创建
     *
     * @param userTask
     */
    void create(PartyUserTask userTask);

    /**
     * 创建
     *
     * @param userTask
     * @param id
     */
    void update(PartyUserTask userTask, String id);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);

}
