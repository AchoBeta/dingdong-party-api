package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.PartyActivityComment;
import com.dingdong.party.activity.entity.vo.CommentEntity;
import com.dingdong.party.activity.service.PartyActivityCommentService;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
@RestController
@Api(tags = "评论管理")
@RequestMapping("/activities/{activityId}/comments")
public class PartyActivityCommentController {

    @Resource
    PartyActivityCommentService commentService;

    @GetMapping("/{id}")
    @ApiOperation("根据id查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评论id", type = "String", required = true)
    })
    public ResponseEntity<Result<CommonItem<PartyActivityComment>>> queryById(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {
        PartyActivityComment comment = commentService.queryById(id);
        return CommonResult.success(CommonItem.restItem(comment));
    }

    @ApiOperation("根据条件分页查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true), @ApiImplicitParam(name = "userId", value = "用户id", type = "String"),
            @ApiImplicitParam(name = "page", value = "分页", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int"),
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<CommentEntity>>> query(@PathVariable(value = "activityId") String activityId,
                                                                   @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<CommentEntity> list = commentService.getList(activityId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @PostMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @ApiOperation("创建评论")
    public ResponseEntity<Result<IdVO>> create(@PathVariable("activityId") String activityId, @RequestBody PartyActivityComment comment) {
        commentService.create(activityId, comment);
        return CommonResult.success(new IdVO(comment.getId()));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评论id", type = "String", required = true)
    })
    public ResponseEntity<Result<IdVO>> update(@PathVariable("activityId") String activityId, @PathVariable("id") String id, @RequestBody PartyActivityComment comment) {
        commentService.update(activityId, id, comment);
        return CommonResult.success(new IdVO(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评论id", type = "String", required = true)
    })
    public ResponseEntity<Result<String>> remove(@PathVariable("activityId") String activityId, @PathVariable String id) {
        commentService.remove(id);
        return CommonResult.success("删除成功");
    }
}

