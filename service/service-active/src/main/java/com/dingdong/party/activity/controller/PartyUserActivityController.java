package com.dingdong.party.activity.controller;


import com.dingdong.party.activity.entity.vo.UserEntity;
import com.dingdong.party.activity.service.PartyUserActivityService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-17
 */
@RestController
@Api(tags = "用户-活动管理")
@RequestMapping("/activities/{activityId}/users")
public class PartyUserActivityController {

    @Resource
    PartyUserActivityService userActivityService;

    @Resource
    RedisTemplate redisTemplate;

    /**
     * 添加一些用户
     * @return
     */
    @ApiOperation("添加一些用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "String", required = true)
    })
    @PostMapping("/add")
    public Result addUsers(@RequestBody List<String> userIds, @PathVariable("activityId") String activityId,
                           @RequestParam("branchId") String branchId, @RequestParam("groupId") String groupId) {
        if (userActivityService.addUsers(userIds, activityId, branchId, groupId)) return Result.ok();
        return Result.error().message("添加失败");
    }

    @ApiOperation("按条件分页查询")
    @GetMapping("")
    public Result query(@RequestParam(value = "user_id", required = false) String userId, @PathVariable String activityId,
                        @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "branchId", required = false) String branchId,
                        @RequestParam(value = "groupId", required = false) String groupId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        HashMap<String, Object> map = userActivityService.query(userId, activityId, status, branchId, groupId, page, size);
        if (map != null) return Result.ok().data("list", map);
        return Result.error().message("暂无数据");
    }

    @ApiOperation("根据活动查找所有用户")
    @GetMapping("/query-by-activityId")
    public Result queryAllUser(@PathVariable("activityId") String activityId) {
        List<UserEntity> list = userActivityService.getAllUser(activityId);
        if (list != null) return Result.ok().data("items", list);
        return Result.error().message("无用户");
    }

    @ApiOperation("活动请假")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true),
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "reason", value = "请假原因", type = "String", required = true)
    })
    @PostMapping("/{userId}/leave")
    public Result leave(@PathVariable("activityId") String activityId, @PathVariable("userId") String userId, @RequestParam(value = "reason", required = false) String reason) {
        if (userActivityService.leave(activityId, userId, reason)) return Result.ok();
        return Result.error().message("请假失败");
    }

    @ApiOperation("申请参与活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true),
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @PostMapping("/{userId}/participate")
    public Result participate(@PathVariable("activityId") String activityId, @PathVariable("userId") String userId) {
        if (userActivityService.participate(activityId, userId)) return Result.ok();
        return Result.error().message("参与失败");
    }

}

