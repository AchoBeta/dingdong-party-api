package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyTeacher;
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
public interface PartyTeacherService extends IService<PartyTeacher> {

    Map<Object, Object> getList(String name, String groupId, String groupName, String branchId, String branchName, String partyPosition, Integer page, Integer size);
}
