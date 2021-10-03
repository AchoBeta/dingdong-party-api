package com.dingdong.party.user.service;

import com.dingdong.party.user.entity.PartyStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dingdong.party.user.entity.vo.StudentEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
public interface PartyStudentService extends IService<PartyStudent> {

    /**
     * 分页
     * @param branchId
     * @param groupId
     * @param institute
     * @param grade
     * @param major
     * @param className
     * @param dormitoryArea
     * @param page
     * @param size
     * @return
     */
    List<StudentEntity> getList(String branchId, String groupId, String institute, String grade, String major, String className, String dormitoryArea, int page, int size);

    /**
     * 查询学生
     * @param studentNo
     * @return
     */
    StudentEntity queryStudentById(String studentNo);

    /**
     * 创建
     * @param studentEntity
     */
    void create(StudentEntity studentEntity);

    /**
     * 更新
     * @param studentId
     * @param studentEntity
     */
    void update(String studentId, StudentEntity studentEntity);

    /**
     * 删除
     * @param id
     */
    void remove(String id);

}
