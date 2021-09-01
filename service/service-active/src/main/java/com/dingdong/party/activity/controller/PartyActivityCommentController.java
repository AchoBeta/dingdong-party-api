package com.dingdong.party.activity.controller;


import com.dingdong.party.activity.entity.PartyActivityComment;
import com.dingdong.party.activity.service.PartyActivityCommentService;
import com.dingdong.party.commonUtils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@RestController
@Api(tags = "评论管理")
@RequestMapping("/activities/{activityId}/comments")
public class PartyActivityCommentController {

    @Autowired
    PartyActivityCommentService commentService;

    @GetMapping("/{id}")
    @ApiOperation("根据id查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评论id", type = "String", required = true)
    })
    public Result queryById(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {
        PartyActivityComment comment = commentService.getById(id);
        if (comment != null) {
            return Result.ok().data("item", comment);
        }
        return Result.error().message("查询失败");
    }

    @ApiOperation("根据条件分页查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true), @ApiImplicitParam(name = "userId", value = "用户id", type = "String"),
            @ApiImplicitParam(name = "page", value = "分页", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int"),
    })
    @GetMapping("")
    public Result query(@PathVariable(value = "activityId") String activityId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = commentService.getList(activityId, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @PostMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @ApiOperation("创建")
    public Result create(@PathVariable("activityId") String activityId, @RequestBody PartyActivityComment comment) {
        comment.setActivityId(activityId);
        if (commentService.save(comment))
            return Result.ok().data("id", comment.getId());
        return Result.error().message("创建失败");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评论id", type = "String", required = true)
    })
    public Result update(@PathVariable("activityId") String activityId, @PathVariable("id") String id, @RequestBody PartyActivityComment comment) {
        comment.setActivityId(activityId);
        comment.setId(id);
        if (commentService.updateById(comment))
            return Result.ok().data("id", id);
        return Result.error().message("修改失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评论id", type = "String", required = true)
    })
    public Result delete(@PathVariable("activityId") String activityId, @PathVariable String id) {
        if (commentService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }
}

