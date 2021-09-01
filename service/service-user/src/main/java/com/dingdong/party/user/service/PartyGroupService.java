package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface PartyGroupService extends IService<PartyGroup> {

    Map<Object, Object> getList(String name, String branchId, String branchName, String directorId, String directorName, Integer page, Integer size);
}
