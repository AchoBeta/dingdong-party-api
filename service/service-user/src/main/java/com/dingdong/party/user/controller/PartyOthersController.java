package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.*;
import com.dingdong.party.serviceBase.common.vo.NumVo;
import com.dingdong.party.user.entity.vo.StageCountEntity;
import com.dingdong.party.user.service.PartyStageService;
import io.swagger.annotations.Api;
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
@Api(tags = "其他管理")
@RequestMapping("/others")
public class PartyOthersController {

    @Resource
    PartyStageService stageService;

    @ApiOperation("获取最高期数")
    @GetMapping("/max-periods")
    public ResponseEntity<Result<NumVo>> queryPeriodsNum() {
        Integer num = stageService.queryPeriodsNum();
        return CommonResult.success(new NumVo(num));
    }

    @ApiOperation("获取最高年级")
    @GetMapping("/max-grade")
    public ResponseEntity<Result<NumVo>> queryGradeNum() {
        Integer num = stageService.queryGradeNum();
        return CommonResult.success(new NumVo(num));
    }

    @ApiOperation("修改最高期数")
    @PutMapping("/max-periods/{id}")
    public ResponseEntity<Result<String>> updatePeriodsNum(@PathVariable String id, Integer periodsNum) {
        stageService.updatePeriodsNum(id, periodsNum);
        return CommonResult.success("修改成功");
    }

    @ApiOperation("修改最高年级")
    @PutMapping("/max-grade/{id}")
    public ResponseEntity<Result<String>> updateGradeNum(@PathVariable String id, Integer gradeNum) {
        stageService.updateGradeNum(id, gradeNum);
        return CommonResult.success("修改成功");
    }

    @ApiOperation("统计各阶段人数")
    @GetMapping("/count")
    public ResponseEntity<Result<CommonItems<StageCountEntity>>> queryStageCount() {
        List<StageCountEntity> list = stageService.queryStageCount();
        return CommonResult.success(CommonItems.restItems(list));
    }

}
