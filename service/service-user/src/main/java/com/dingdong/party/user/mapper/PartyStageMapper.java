package com.dingdong.party.user.mapper;

import com.dingdong.party.user.entity.PartyStage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingdong.party.user.entity.vo.StageCountEntity;
import com.dingdong.party.user.entity.vo.StageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Mapper
public interface PartyStageMapper extends BaseMapper<PartyStage> {

    @Select("select id, name, description from party_stage")
    List<StageEntity> selectAll();

    @Select("select num from party_others where id = #{id}")
    Integer queryNum(String id);

    List<StageCountEntity> queryStageCount();
}
