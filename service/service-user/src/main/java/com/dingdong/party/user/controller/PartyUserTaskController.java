package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyUserTask;
import com.dingdong.party.user.service.PartyUserTaskService;
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
 * @since 2021-08-12
 */
@RestController
@Api(tags = "用户任务管理")
@RequestMapping("/user-tasks")
public class PartyUserTaskController {

    @Autowired
    PartyUserTaskService userTaskService;

    @ApiOperation("按 id 查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户阶段id", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") String id) {
        PartyUserTask userTask = userTaskService.getById(id);
        if (userTask != null)
            return Result.ok().data("item", userTask);
        return Result.error().message("用户阶段信息查询失败");
    }

    @ApiOperation("按条件查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id"), @ApiImplicitParam(name = "task_id", value = "任务id"),
            @ApiImplicitParam(name = "page", value = "页号"), @ApiImplicitParam(name = "size", value = "大小")
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "task_id", required = false) Integer taskId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = userTaskService.getList(userId, taskId, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @ApiOperation("创建用户阶段信息")
    @PostMapping("")
    public Result create(@RequestBody PartyUserTask userTask) {
        if (userTaskService.save(userTask))
            return Result.ok().data("id", userTask.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/{id}")
    public Result update(@RequestBody PartyUserTask userTask, @PathVariable("id") String id) {
        userTask.setId(id);
        if (userTaskService.updateById(userTask))
            return Result.ok();
        return Result.error().message("更新失败");
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        if (userTaskService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }
}

