package com.dingdong.party.activity.mapper;

import com.dingdong.party.activity.entity.PartyActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@Mapper
public interface PartyActivityMapper extends BaseMapper<PartyActivity> {

    List<Map<Object, Object>> getList(@Param("name") String name,
                                      @Param("branchId") String branchId,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime,
                                      @Param("directorId") String directorId,
                                      @Param("directorName") String directorName,
                                      @Param("status") Integer status);

    @Update("update party_activity set status = 1 where id = #{activityId}")
    boolean commit(String activityId);
}
