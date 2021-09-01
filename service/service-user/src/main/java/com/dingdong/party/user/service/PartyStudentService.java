package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyStudent;
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
public interface PartyStudentService extends IService<PartyStudent> {

    Map<Object, Object> getList(String branchId, String groupId, String institute, String grade, String major, String className, String dormitoryArea, int page, int size);
}
