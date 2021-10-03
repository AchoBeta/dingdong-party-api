package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyUserStage;
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
 * @since 2021-08-12
 */
public interface PartyUserStageService extends IService<PartyUserStage> {

    /**
     * 分页
     * @param userId
     * @param stageId
     * @param page
     * @param size
     * @return
     */
    List<PartyUserStage> getList(String userId, Integer stageId, Integer page, Integer size);

    /**
     * 查询
     * @param id
     * @return
     */
    PartyUserStage queryById(String id);

    /**
     * 创建
     * @param userStage
     */
    void create(PartyUserStage userStage);

    /**
     * 更新
     * @param userStage
     * @param id
     */
    void update(PartyUserStage userStage, String id);

    /**
     * 删除
     * @param id
     */
    void remove(String id);
}
