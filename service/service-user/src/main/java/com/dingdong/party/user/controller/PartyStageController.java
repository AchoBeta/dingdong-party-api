package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.*;
import com.dingdong.party.user.entity.PartyStage;
import com.dingdong.party.user.service.PartyStageService;
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
 *  前端控制器
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@RestController
@Api(tags = "入党阶段管理")
@RequestMapping("/stages")
public class PartyStageController {

    @Resource
    PartyStageService partyStageService;

    @Resource
    PartyTaskService taskService;

    @ApiOperation("根据id查询入党阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入党阶段id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyStage>>> queryById(@PathVariable String id) {
        PartyStage stage = partyStageService.queryById(id);
        return CommonResult.success(CommonItem.restItem(stage));
    }

    @PostMapping("")
    @ApiOperation("创建入党阶段")
    public ResponseEntity<Result<String>> create(@RequestBody PartyStage stage) {
        partyStageService.create(stage);
        return CommonResult.success("创建成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除入党阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入党阶段id", type = "String", required = true)
    })
    public ResponseEntity<Result<String>> remove(@PathVariable String id) {
        partyStageService.remove(id);
        return CommonResult.success("删除成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("修改入党阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入党阶段id", type = "String", required = true)
    })
    public ResponseEntity<Result<String>> update(@PathVariable("id") Integer id, @RequestBody PartyStage stage) {
        stage.setId(id);
        partyStageService.update(stage);
        return CommonResult.success("更新成功");
    }

    @ApiOperation("获取所有入党阶段")
    @GetMapping("")
    public ResponseEntity<Result<CommonItems<PartyStage>>> queryAllStageAndTask() {
        List<PartyStage> list = partyStageService.queryAllStageAndTask();
        return CommonResult.success(CommonItems.restItems(list));
    }

}

