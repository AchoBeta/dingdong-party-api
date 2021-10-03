package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityImage;
import com.dingdong.party.activity.entity.PartyActivityImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
public interface PartyActivityImageService extends IService<PartyActivityImage> {

    /**
     * 分页
     *
     * @param activityId
     * @param page
     * @param size
     * @return
     */
    List<PartyActivityImage> getList(String activityId, Integer page, Integer size);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    PartyActivityImage queryById(String id);

    /**
     * 创建
     *
     * @param activityId
     * @param image
     * @return
     */
    void create(String activityId, PartyActivityImage image);

    /**
     * 更新
     *
     * @param activityId
     * @param id
     * @param image
     */
    void update(String activityId, String id, PartyActivityImage image);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);
}
