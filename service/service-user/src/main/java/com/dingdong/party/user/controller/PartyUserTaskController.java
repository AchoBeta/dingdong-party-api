package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.entity.PartyUserTask;
import com.dingdong.party.user.service.PartyUserTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author retraci
 * @since 2021-08-12
 */
@RestController
@Api(tags = "用户任务管理")
@RequestMapping("/user-tasks")
public class PartyUserTaskController {

    @Resource
    PartyUserTaskService userTaskService;

    @ApiOperation("按 id 查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户阶段id", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyUserTask>>> queryById(@PathVariable("id") String id) {
        PartyUserTask userTask = userTaskService.queryById(id);
        return CommonResult.success(CommonItem.restItem(userTask));
    }

    @ApiOperation("按条件查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id"), @ApiImplicitParam(name = "task_id", value = "任务id"),
            @ApiImplicitParam(name = "page", value = "页号"), @ApiImplicitParam(name = "size", value = "大小")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyUserTask>>> query(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "task_id", required = false) Integer taskId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<PartyUserTask> list = userTaskService.getList(userId, taskId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建用户阶段信息")
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@RequestBody PartyUserTask userTask) {
        userTaskService.create(userTask);
        return CommonResult.success(new IdVO(userTask.getId()));
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@RequestBody PartyUserTask userTask, @PathVariable("id") String id) {
        userTaskService.update(userTask, id);
        return CommonResult.success(new IdVO(id));
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable("id") String id) {
        userTaskService.remove(id);
        return CommonResult.success("删除成功");
    }
}

