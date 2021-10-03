package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.activity.entity.vo.CommentEntity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
public interface PartyActivityCommentService extends IService<PartyActivityComment> {

    /**
     * 分页
     *
     * @param activityId
     * @param page
     * @param size
     * @return
     */
    List<CommentEntity> getList(String activityId, Integer page, Integer size);


    /**
     * 查询
     *
     * @param id
     * @return
     */
    PartyActivityComment queryById(String id);

    /**
     * 创建
     *
     * @param activityId
     * @param comment
     * @return
     */
    void create(String activityId, PartyActivityComment comment);

    /**
     * 更新
     *
     * @param activityId
     * @param id
     * @param comment
     */
    void update(String activityId, String id, PartyActivityComment comment);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);
}
