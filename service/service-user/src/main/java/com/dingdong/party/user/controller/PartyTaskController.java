package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyTask;
import com.dingdong.party.user.service.PartyTaskService;
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
 * @since 2021-07-23
 */
@RestController
@Api(tags = "阶段任务管理")
@RequestMapping("/stages/{stageId}/tasks")
public class PartyTaskController {

    @Autowired
    PartyTaskService taskService;

    @ApiOperation("根据任务id获取任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "id", value = "任务id", type = "int", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("stageId") int stageId, @PathVariable("id") String id) {
        PartyTask task = taskService.getById(id);
        if (task != null) {
            return Result.ok().data("item", task);
        }
        return Result.error().message("查询失败");
    }

    @ApiOperation("按条件分页查询任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "任务名称", type = "String"), @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "name", required = false) String name, @PathVariable(value = "stageId", required = false) int stageId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<Object, Object> map = taskService.getList(name, stageId, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @ApiOperation("删除阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "id", value = "任务id", type = "int", required = true)
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("stageId") String stageId, @PathVariable("id") String id) {
        if (taskService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @ApiOperation("创建阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true)
    })
    @PostMapping("")
    public Result create(@PathVariable("stageId") Integer stageId, @RequestBody PartyTask task) {
        task.setStageId(stageId);
        if (taskService.save(task))
            return Result.ok().data("id", task.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("修改阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "id", value = "任务id", type = "int", required = true)
    })
    @PatchMapping("/{id}")
    public Result update(@PathVariable("stageId") Integer stageId, @PathVariable("id") Integer id, @RequestBody PartyTask task) {
        task.setStageId(stageId);
        task.setId(id);
        if (taskService.updateById(task))
            return Result.ok().data("id", id);
        return Result.error().message("修改失败");
    }
}

