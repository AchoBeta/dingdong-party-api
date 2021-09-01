package com.dingdong.party.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingdong.party.activity.entity.PartyActivityComment;
import com.dingdong.party.activity.entity.vo.CommentEntity;
import com.dingdong.party.activity.mapper.PartyActivityCommentMapper;
import com.dingdong.party.activity.service.PartyActivityCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@Service
public class PartyActivityCommentServiceImpl extends ServiceImpl<PartyActivityCommentMapper, PartyActivityComment> implements PartyActivityCommentService {

    @Autowired
    PartyActivityCommentMapper commentMapper;

    @Override
    public Map<String, Object> getList(String activityId, Integer page, Integer size) {
        System.out.println(activityId);
        HashMap<String, Object> map = new HashMap<>(2);
        long total = commentMapper.countByActivityId(activityId);
        List<CommentEntity> list = commentMapper.queryByActivityId(activityId, (page-1)*size, size);
        map.put("total", total);
        map.put("items", list);
        return map;
    }
}
