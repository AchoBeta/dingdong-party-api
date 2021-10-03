package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyGroupService extends IService<PartyGroup> {

    /**
     * 分页
     * @param name
     * @param branchId
     * @param branchName
     * @param directorId
     * @param directorName
     * @param page
     * @param size
     * @return
     */
    List<PartyGroup> getList(String name, String branchId, String branchName, String directorId, String directorName, Integer page, Integer size);

    /**
     * 查询
     * @param id
     * @return
     */
    PartyGroup queryById(String id);

    /**
     * 创建
     * @param group
     */
    void create(PartyGroup group);

    /**
     * 删除
     * @param id
     */
    void remove(String id);

    /**
     * 更新
     * @param group
     */
    void update(PartyGroup group);

}
