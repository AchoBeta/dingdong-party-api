package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyBranch;
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
public interface PartyBranchService extends IService<PartyBranch> {

    /**
     * 分页 查询多个
     * @param name
     * @param parentId
     * @param parentName
     * @param directorId
     * @param directorName
     * @param page
     * @param size
     * @return
     */
    List<PartyBranch> getList(String name, String parentId, String parentName, String directorId, String directorName, Integer page, Integer size);

    /**
     * 通过 id 查询
     * @param id
     * @return
     */
    PartyBranch queryById(String id);

    /**
     * 创建
     * @param branch
     * @return
     */
    void create(PartyBranch branch);

    /**
     * 删除
     * @param id
     * @return
     */
    void remove(String id);

    /**
     * 更新
     * @param branch
     * @return
     */
    void update(PartyBranch branch);

}
