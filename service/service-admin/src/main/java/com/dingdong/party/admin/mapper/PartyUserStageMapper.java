package com.dingdong.party.admin.mapper;

import com.dingdong.party.admin.entity.PartyUserStage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
@Mapper
public interface PartyUserStageMapper extends BaseMapper<PartyUserStage> {



    @Update("update party_user set stage_id = #{arg1} where user_id = #{arg0}")
    boolean updateStage(String userId, Integer stageId);

    @Select({
        "<script>",
            "select user_id from party_user where status = 1 AND is_deleted = 0",
            "<when test='branchId != null'>",
                "AND branch_id = #{branchId}",
            "</when>",
            "<when test='groupId != null'>",
                "AND group_id = #{groupId}",
            "</when>",
            "<when test='stage != null'>",
                "AND stage = #{stage}",
            "</when>",
            "<when test='institute != null || grade != null || major != null'>",
                "AND student_id = (select student_id from party_student where is_deleted = 0",
                "<when test='institute != null'>",
                    "AND institute = #{institute}",
                "</when>",
                "<when test='grade != null'>",
                    "AND grade = #{grade}",
                "</when>",
                "<when test='major != null'>",
                    "AND major = #{major}",
                "</when>",
            "</when>",
        "</script>"})
    List<String> getUser(@Param("branchId") String branchId, @Param("groupId") String groupId, @Param("stage") Integer stage,
                         @Param("institute") String institute, @Param("grade") Integer grade, @Param("major") String major);

    @Select("select user_id where user_id = #{arg0} and stage_id = #{arg1} and is_deleted = 0")
    String getUserStage(String userId, Integer stageId);

    // 更新用户阶段表
    @Update("update party_user_stage time = #{arg1}, where id = #{arg0}")
    boolean updateUserStage(String id, Date time);

    // 插入用户阶段表
    @Insert("insert into party_user (user_id, stage_id, time) values (#{arg0}, #{arg1}, #{arg2}")
    boolean insertUserStage(String userId, Integer stageId, Date time);
}
