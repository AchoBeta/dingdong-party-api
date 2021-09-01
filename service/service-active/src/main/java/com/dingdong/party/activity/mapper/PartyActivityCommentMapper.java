package com.dingdong.party.activity.mapper;

import com.dingdong.party.activity.entity.PartyActivityComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.activity.entity.vo.CommentEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
public interface PartyActivityCommentMapper extends BaseMapper<PartyActivityComment> {

    @Select("select count(*) from party_activity_comment where activity_id = #{activityId}")
    long countByActivityId(String activityId);

    @Select("select a.*, b.name from " +
            "(select id, activity_id as activityId, user_id as userId, content, image, create_time as time " +
            "from party_activity_comment where activity_id = #{arg0}) as a, " +
            "(select user_id, name from party_user) as b " +
            "where a.userId = b.user_id " +
            "limit #{arg1}, #{arg2}")
    List<CommentEntity> queryByActivityId(String activityId, int i, int size);
}
