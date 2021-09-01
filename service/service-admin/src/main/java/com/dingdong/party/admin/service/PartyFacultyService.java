package com.dingdong.party.admin.service;

import com.dingdong.party.admin.entity.PartyFaculty;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-08
 */
public interface PartyFacultyService extends IService<PartyFaculty> {

    public List<PartyFaculty> query(String pid);
}
