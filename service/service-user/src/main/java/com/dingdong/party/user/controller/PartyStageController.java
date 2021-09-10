package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyStage;
import com.dingdong.party.user.service.PartyStageService;
import com.dingdong.party.user.service.PartyTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@Api(tags = "入党阶段管理")
@RequestMapping("/stages")
public class PartyStageController {

    @Autowired
    PartyStageService partyStageService;

    @Autowired
    PartyTaskService taskService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("根据id查询入党阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入党阶段id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable String id) {
        PartyStage stage = partyStageService.getById(id);
        if (stage != null){
            return Result.ok().data("item", stage);
        }
        return Result.error().message("无此记录");
    }

    @PostMapping("")
    @ApiOperation("创建入党阶段")
    public Result create(@RequestBody PartyStage stage) {
        if (partyStageService.save(stage)) {
            return Result.ok();
        }
        return Result.error().message("创建失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除入党阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入党阶段id", type = "String", required = true)
    })
    public Result delete(@PathVariable String id) {
        if (partyStageService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @PutMapping("/{id}")
    @ApiOperation("修改入党阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入党阶段id", type = "String", required = true)
    })
    public Result update(@PathVariable("id") Integer id, @RequestBody PartyStage stage) {
        stage.setId(id);
        if (partyStageService.updateById(stage))
            return Result.ok();
        return Result.error().message("更新失败");
    }

    @ApiOperation("获取所有入党阶段")
    @GetMapping("")
    public Result queryAllStageAndTask() {
        List<PartyStage> list = partyStageService.queryAllStageAndTask();
        if (list != null)
            return Result.ok().data("items", list);
        return Result.error().message("获取失败");
    }

}

