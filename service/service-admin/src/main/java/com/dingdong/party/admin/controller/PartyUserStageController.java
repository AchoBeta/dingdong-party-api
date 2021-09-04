package com.dingdong.party.admin.controller;


import com.dingdong.party.admin.service.PartyUserStageService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/user-stages")
@Api(tags = "阶段管理")
public class PartyUserStageController extends BaseController {

    @Autowired
    PartyUserStageService userStageService;

    @ApiOperation("用户批量进入下一阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id数组", type = "String[]", required = true),
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "time", value = "进入阶段时间", type = "Date", required = true),
            @ApiImplicitParam(name = "groupIds", value = "党小组id", type = "String", required = true)
    })
    @PostMapping("/change")
    public Result changeUserStage(@RequestParam("userIds") String[] userIds, @RequestParam("stageId") Integer stageId, @RequestParam("time") Date time, @RequestParam("groupIds") String[] groupIds, HttpServletRequest request) {
        String token = request.getHeader("token");
        for (String groupId : groupIds) {
            if (getGroupAdminAuthority(token, groupId) < 0)
                return Result.error(ResultCode.NO_PERMISSION);
        }
        if (userStageService.changeUserStage(userIds, stageId, time))
            return Result.ok();
        return Result.error().message("更改失败");
    }


    @ApiOperation("回退阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id数组", type = "String[]", required = true),
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "oldTaskId", value = "原任务id", type = "int", required = true),
            @ApiImplicitParam(name = "newTaskId", value = "新任务id", type = "int", required = true),
            @ApiImplicitParam(name = "groupId", value = "党支部id", type = "String", required = true)
    })
    @PostMapping("back")
    public Result back(@RequestParam("userIds") String[] userIds, @RequestParam("stageId") Integer stageId, @RequestParam("oldTaskId") Integer oldTaskId,
                       @RequestParam("newTaskId") Integer newTaskId, @RequestParam("groupId") String groupId, HttpServletRequest request) {
        if (oldTaskId < newTaskId)
            return Result.error().message("只能回退到上面的版本");
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > -1) {
            if (userStageService.back(userIds, stageId, newTaskId))
                return Result.ok();
            return Result.error().message("回退失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("批量修改阶段时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id数组", type = "String[]", required = true),
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "time", value = "时间", type = "Date", required = true),
            @ApiImplicitParam(name = "groupId", value = "党支部id", type = "String", required = true)
    })
    @PostMapping("batch-update-time")
    public Result updateTime(@RequestParam("userIds") String[] userIds, @RequestParam("stageId") String stageId,
                             @RequestParam("time") Date time, @RequestParam("groupId") String groupId,  HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > -1) {
            if (userStageService.updateTime(userIds, stageId, time))
                return Result.ok();
            return Result.error().message("修改失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }
}

