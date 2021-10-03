package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.activity.entity.vo.ActivityDetailsEntity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
public interface PartyActivityService extends IService<PartyActivity> {

    /**
     * 分页
     *
     * @param name
     * @param branchId
     * @param startTime
     * @param endTime
     * @param directorId
     * @param directorName
     * @param status
     * @param page
     * @param size
     * @return
     */
    List<PartyActivity> getList(String name, String branchId, String startTime, String endTime, String directorId, String directorName, Integer status, Integer page, Integer size);

    /**
     * 获取全部
     *
     * @param page
     * @param size
     * @return
     */
    List<PartyActivity> queryAll(Integer page, Integer size);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    ActivityDetailsEntity queryById(String id);

    /**
     * 创建
     *
     * @param activity
     */
    void create(ActivityDetailsEntity activity);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);

    /**
     * 更新
     *
     * @param id
     * @param detailsEntity
     */
    void update(String id, ActivityDetailsEntity detailsEntity);

    /**
     * 提交
     *
     * @param activityId
     */
    void commit(String activityId);

}
