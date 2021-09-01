package com.dingdong.party.activity.mapper;

import com.dingdong.party.activity.entity.PartyActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
public interface PartyActivityMapper extends BaseMapper<PartyActivity> {

    @Update("update party_activity set status = 1 where activity_id = #{activityId}")
    boolean commit(String activityId);
}
