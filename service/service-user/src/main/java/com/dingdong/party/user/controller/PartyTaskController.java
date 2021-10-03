package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.user.entity.PartyTask;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.entity.vo.TaskEntity;
import com.dingdong.party.user.service.PartyTaskService;
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
 * @since 2021-07-23
 */
@RestController
@Api(tags = "阶段任务管理")
@RequestMapping("/stages/{stageId}/tasks")
public class PartyTaskController {

    @Resource
    PartyTaskService taskService;

    @ApiOperation("根据任务id获取任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "id", value = "任务id", type = "int", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyTask>>> queryById(@PathVariable("stageId") int stageId, @PathVariable("id") String id) {
        PartyTask task = taskService.queryById(id);
        return CommonResult.success(CommonItem.restItem(task));
    }

    @ApiOperation("按条件分页查询任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "任务名称", type = "String"), @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<TaskEntity>>> query(@RequestParam(value = "name", required = false) String name, @PathVariable(value = "stageId", required = false) int stageId,
                                                               @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<TaskEntity> list = taskService.getList(name, stageId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("删除阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "id", value = "任务id", type = "int", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable("stageId") String stageId, @PathVariable("id") String id) {
        taskService.remove(id);
        return CommonResult.success("删除成功");
    }

    @ApiOperation("创建阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true)
    })
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@PathVariable("stageId") Integer stageId, @RequestBody PartyTask task) {
        task.setStageId(stageId);
        taskService.create(task);
        return CommonResult.success(new IdVO(task.getId().toString()));
    }

    @ApiOperation("修改阶段任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int", required = true),
            @ApiImplicitParam(name = "id", value = "任务id", type = "int", required = true)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable("stageId") Integer stageId, @PathVariable("id") Integer id, @RequestBody PartyTask task) {
        task.setStageId(stageId);
        task.setId(id);
        taskService.update(task);
        return CommonResult.success(new IdVO(id.toString()));
    }
}

