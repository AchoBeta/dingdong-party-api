package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.vo.UserActivityVO;
import com.dingdong.party.activity.entity.vo.UserEntity;
import com.dingdong.party.activity.service.PartyUserActivityService;

import com.dingdong.party.serviceBase.common.api.CommonItems;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
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
 * @since 2021-07-17
 */
@RestController
@Api(tags = "用户-活动管理")
@RequestMapping("/activities/{activityId}/users")
public class PartyUserActivityController {

    @Resource
    PartyUserActivityService userActivityService;

    /**
     * 添加一些用户
     */
    @ApiOperation("添加一些用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "String", required = true)
    })
    @PostMapping("/add")
    public ResponseEntity<Result<String>> addUsers(@RequestBody List<String> userIds, @PathVariable("activityId") String activityId,
                                                   @RequestParam("branchId") String branchId, @RequestParam("groupId") String groupId) {
        userActivityService.addUsers(userIds, activityId, branchId, groupId);
        return CommonResult.success("添加成功");
    }

    @ApiOperation("按条件分页查询")
    @GetMapping("")
    public ResponseEntity<Result<CommonList<UserActivityVO>>> query(@RequestParam(value = "userId", required = false) String userId, @PathVariable String activityId,
                                                                        @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "branchId", required = false) String branchId,
                                                                        @RequestParam(value = "groupId", required = false) String groupId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<UserActivityVO> list = userActivityService.query(userId, activityId, status, branchId, groupId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("根据活动查找所有用户")
    @GetMapping("/query-by-activityId")
    public ResponseEntity<Result<CommonItems<UserEntity>>> queryAllUser(@PathVariable("activityId") String activityId) {
        List<UserEntity> list = userActivityService.getAllUser(activityId);
        return CommonResult.success(CommonItems.restItems(list));

    }

    @ApiOperation("活动请假")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true),
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "reason", value = "请假原因", type = "String", required = true)
    })
    @PostMapping("/{userId}/leave")
    public ResponseEntity<Result<String>> leave(@PathVariable("activityId") String activityId, @PathVariable("userId") String userId, @RequestParam(value = "reason", required = false) String reason) {
        userActivityService.leave(activityId, userId, reason);
        return CommonResult.success("请假成功");
    }

    @ApiOperation("申请参与活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true),
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @PostMapping("/{userId}/participate")
    public ResponseEntity<Result<String>> participate(@PathVariable("activityId") String activityId, @PathVariable("userId") String userId) {
        userActivityService.participate(activityId, userId);
        return CommonResult.success("申请参与成功");

    }

}

