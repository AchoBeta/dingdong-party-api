package com.dingdong.party.user.controller;


import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyUserStage;
import com.dingdong.party.user.service.PartyUserStageService;
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
@Api(tags = "用户阶段管理")
@RequestMapping("/user-stages")
public class PartyUserStageController {

    @Autowired
    PartyUserStageService userStageService;

    @ApiOperation("按 id 查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户阶段id", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") String id) {
        PartyUserStage userStage = userStageService.getById(id);
        if (userStage != null)
            return Result.ok().data("item", userStage);
        return Result.error().message("用户阶段信息查询失败");
    }

    @ApiOperation("按条件查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id"), @ApiImplicitParam(name = "stage_id", value = "阶段id"),
            @ApiImplicitParam(name = "page", value = "页号"), @ApiImplicitParam(name = "size", value = "大小")
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "stage_id", required = false) Integer stageId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
         Map<String, Object> map = userStageService.getList(userId, stageId, page, size);
         if (map != null)
             return Result.ok().data("list", map);
         return Result.error().message("查询失败");
    }

    @ApiOperation("创建用户阶段信息")
    @PostMapping("")
    public Result create(@RequestBody PartyUserStage userStage) {
        if (userStageService.save(userStage))
            return Result.ok().data("id", userStage.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/{id}")
    public Result update(@RequestBody PartyUserStage userStage, @PathVariable("id") String id) {
        userStage.setId(id);
        if (userStageService.updateById(userStage))
            return Result.ok();
        return Result.error().message("更新失败");
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        if (userStageService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }
}

