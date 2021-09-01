package com.dingdong.party.admin.controller;

import com.dingdong.party.admin.service.PartyUserTaskService;
import com.dingdong.party.admin.service.WXService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "任务管理")
@RequestMapping("/tasks")
public class PartyUserTaskController extends BaseController {

    @Autowired
    WXService wxService;

    @GetMapping("/a")
    public Result a() throws Exception {
        wxService.uniformMessage("oDGfk5IdOEaQFWl8sORsUFnNXYnQ");
        return Result.ok();
    }

    @Autowired
    PartyUserTaskService userTaskService;

    @ApiOperation("用户批量进入下一任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id数组", type = "String[]", required = true),
            @ApiImplicitParam(name = "taskId", value = "任务id", type = "int", required = true),
            @ApiImplicitParam(name = "time", value = "进入阶段时间", type = "Date", required = true),
            @ApiImplicitParam(name = "groupId", value = "党支部id", type = "String", required = true)
    })
    @PostMapping("/change")
    public Result changeUserStage(@RequestParam("userIds") String[] userIds, @RequestParam("taskId") Integer taskId, @RequestParam("time") Date time, @RequestParam("groupId") String groupId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > -1) {
            if (userTaskService.changeUserTask(userIds, taskId, time))
                return Result.ok();
            return Result.error().message("更改失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("批量修改提交任务时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id数组", type = "String[]", required = true),
            @ApiImplicitParam(name = "taskId", value = "任务id", type = "int", required = true),
            @ApiImplicitParam(name = "time", value = "时间", type = "Date", required = true),
            @ApiImplicitParam(name = "groupId", value = "党支部id", type = "String", required = true)
    })
    @PostMapping("batch-update-time")
    public Result updateTime(@RequestParam("userIds") String[] userIds, @RequestParam("taskId") String taskId,
                             @RequestParam("time") Date time, @RequestParam("groupId") String groupId,  HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > -1) {
            if (userTaskService.updateTime(userIds, taskId, time))
                return Result.ok();
            return Result.error().message("修改失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }
}

