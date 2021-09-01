package com.dingdong.party.activity.service;

import com.dingdong.party.activity.entity.PartyActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.activity.entity.vo.ActivityDetailsEntity;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
public interface PartyActivityService extends IService<PartyActivity> {

    Map<Object, Object> getList(String name, String groupId, String branchId, String startTime, String endTime, String directorId, String directorName, Integer status, Integer page, Integer size);

    String create(ActivityDetailsEntity activity) throws Exception;

    boolean deleteById(String id);

    boolean modify(ActivityDetailsEntity detailsEntity);

    boolean commit(String activityId);

    // 小程序端显示所有活动
    Map<String, Object> queryAll(Integer page, Integer size);
}
