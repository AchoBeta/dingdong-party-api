package com.dingdong.party.activity.service.impl;

import com.dingdong.party.activity.entity.PartyActivityComment;
import com.dingdong.party.activity.entity.vo.CommentEntity;
import com.dingdong.party.activity.mapper.PartyActivityCommentMapper;
import com.dingdong.party.activity.service.PartyActivityCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingdong.party.serviceBase.common.api.ResultCode;
import com.dingdong.party.serviceBase.exception.PartyException;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
@Service
public class PartyActivityCommentServiceImpl extends ServiceImpl<PartyActivityCommentMapper, PartyActivityComment> implements PartyActivityCommentService {

    @Resource
    PartyActivityCommentMapper commentMapper;

    @Override
    public List<CommentEntity> getList(String activityId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return commentMapper.queryByActivityId(activityId);
    }

    @Override
    public PartyActivityComment queryById(String id) {
        PartyActivityComment comment = this.getById(id);
        if (comment == null) {
            throw new PartyException("暂无数据", ResultCode.COMMON_FAIL.getCode());
        }

        return comment;
    }

    @Override
    public void create(String activityId, PartyActivityComment comment) {
        comment.setActivityId(activityId);

        boolean res = this.save(comment);
        if (!res) {
            throw new PartyException("创建失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void update(String activityId, String id, PartyActivityComment comment) {
        comment.setActivityId(activityId);
        comment.setId(id);

        boolean res = this.updateById(comment);
        if (!res) {
            throw new PartyException("更新失败", ResultCode.COMMON_FAIL.getCode());
        }
    }

    @Override
    public void remove(String id) {
        boolean res = this.removeById(id);
        if (!res) {
            throw new PartyException("删除失败", ResultCode.COMMON_FAIL.getCode());
        }
    }
}
