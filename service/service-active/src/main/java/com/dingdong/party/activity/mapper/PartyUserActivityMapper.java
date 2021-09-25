package com.dingdong.party.activity.mapper;

import com.dingdong.party.activity.entity.PartyUserActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.activity.entity.vo.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-17
 */
@Mapper
public interface PartyUserActivityMapper extends BaseMapper<PartyUserActivity> {

    List<UserEntity> getAllUser(String activityId);

    List<Map<String, Object>> query(@Param("userId") String userId,
                                    @Param("activityId") String activityId,
                                    @Param("status") Integer status,
                                    @Param("branchId") String branchId,
                                    @Param("groupId") String groupId);

    @Update("update party_user_activity set status = 1, reason = #{arg2} where user_id = #{arg1} and activity_id = #{arg0}")
    boolean leave(String activityId, String userId, String reason);
}
