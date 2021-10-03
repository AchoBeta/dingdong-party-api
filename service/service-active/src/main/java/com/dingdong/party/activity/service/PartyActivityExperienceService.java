package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivityComment;
import com.dingdong.party.activity.entity.PartyActivityExperience;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
public interface PartyActivityExperienceService extends IService<PartyActivityExperience> {

    /**
     * 分页
     *
     * @param activityId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    List<PartyActivityExperience> getList(String activityId, String userId, Integer page, Integer size);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    PartyActivityExperience queryById(String id);

    /**
     * 创建
     *
     * @param activityId
     * @param experience
     * @return
     */
    void create(String activityId, PartyActivityExperience experience);

    /**
     * 更新
     *
     * @param activityId
     * @param id
     * @param experience
     */
    void update(String activityId, String id, PartyActivityExperience experience);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);
}
