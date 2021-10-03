package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyTeacherService extends IService<PartyTeacher> {

    /**
     * 分页
     * @param name
     * @param groupId
     * @param groupName
     * @param branchId
     * @param branchName
     * @param partyPosition
     * @param page
     * @param size
     * @return
     */
    List<TeacherEntity> getList(String name, String groupId, String groupName, String branchId, String branchName, String partyPosition, Integer page, Integer size);

    /**
     * 查询
     * @param id
     * @return
     */
    TeacherEntity queryById(String id);

    /**
     * 创建
     * @param teacherEntity
     */
    void create(TeacherEntity teacherEntity);

    /**
     * 更新
     * @param id
     * @param teacherEntity
     */
    void update(String id, TeacherEntity teacherEntity);

    /**
     * 删除
     * @param id
     */
    void remove(String id);
}
