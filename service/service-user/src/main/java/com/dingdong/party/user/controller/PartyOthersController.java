package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.vo.StageCountEntity;
import com.dingdong.party.user.service.PartyStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "其他管理")
@RequestMapping("/others")
public class PartyOthersController {

    @Autowired
    PartyStageService stageService;

    @ApiOperation("获取最高期数")
    @GetMapping("/max-periods")
    public Result queryPeriodsNum() {
        Integer num = stageService.queryPeriodsNum();
        return Result.ok().data("num", num);
    }

    @ApiOperation("获取最高年级")
    @GetMapping("/max-grade")
    public Result queryGradeNum() {
        Integer num = stageService.queryGradeNum();
        return Result.ok().data("num", num);
    }

    @ApiOperation("统计各阶段人数")
    @GetMapping("/count")
    public Result queryStageCount() {
        List<StageCountEntity> list = stageService.queryStageCount();
        if (list != null)
            return Result.ok().data("items", list);
        return Result.error().message("获取失败");
    }

}
