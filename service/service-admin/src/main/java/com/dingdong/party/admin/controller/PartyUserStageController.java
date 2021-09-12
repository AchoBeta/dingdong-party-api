package com.dingdong.party.admin.controller;


import com.dingdong.party.admin.service.PartyUserStageService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@RequestMapping("/user-stages")
@Api(tags = "阶段管理")
public class PartyUserStageController extends BaseController {

    @Autowired
    PartyUserStageService userStageService;

    @ApiOperation("批量修改阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String"),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "int"),
            @ApiImplicitParam(name = "stage", value = "期数", type = "Integer"),
            @ApiImplicitParam(name = "stageId", value = "所属阶段", type = "Integer", required = true),
            @ApiImplicitParam(name = "userIds", value = "用户数组（当有条件时，此为不要的用户，无条件是为更改的用户）", type = "List"),
            @ApiImplicitParam(name = "time", value = "时间", type = "Date", required = true)
    })
    @PostMapping("batch-update-time")
    public Result updateStage(@RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId,
                             @RequestParam(value = "stageId") Integer stageId, @RequestBody(required = false) String[] userIds,
                             @RequestParam("time") String time, @RequestParam(value = "stage", required = false) Integer stage) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        if (branchId == null && groupId == null && stage == null) {
            if (userStageService.updateStageByUserIds(userIds, stageId, date))
                return Result.ok();
        } else {
            if (userStageService.updateStageByCondition(branchId, groupId, stage, stageId, userIds, date))
                return Result.ok();
        }
        return Result.error().message("修改失败");
    }
}