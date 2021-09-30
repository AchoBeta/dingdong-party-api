package com.dingdong.party.admin.controller;


import com.dingdong.party.admin.service.PartyUserStageService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
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

    @Resource
    PartyUserStageService userStageService;

    @ApiOperation("批量修改阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "党组id", dataType = "int"),
            @ApiImplicitParam(name = "stage", value = "期数", dataType  = "int"),
            @ApiImplicitParam(name = "stageId", value = "修改阶段", dataType  = "int", required = true),
            @ApiImplicitParam(name = "institute", value = "学院名称", dataType = "String"),
            @ApiImplicitParam(name = "major", value = "专业名称", dataType = "String"),
            @ApiImplicitParam(name = "grade", value = "年级", dataType = "Integer"),
            @ApiImplicitParam(name = "userIds", value = "用户数组（当有条件时，此为不要的用户，无条件是为更改的用户）", type = "List"),
            @ApiImplicitParam(name = "time", value = "时间", dataType = "Date", required = true)
    })
    @PostMapping("batch-update-time")
    public Result updateStage(@RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId,
                              @RequestParam(value = "stageId") Integer stageId, @RequestBody() String[] userIds,
                              @RequestParam(value = "institute", required = false) String institute, @RequestParam(value = "grade", required = false) Integer grade,
                              @RequestParam(value = "major", required = false) String major, @RequestParam("time") String time,
                              @RequestParam(value = "stage", required = false) Integer stage) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        if (branchId == null && groupId == null && stage == null && institute != null && grade != null && major != null) {
            if (userStageService.updateStageByUserIds(userIds, stageId, date))
                return Result.ok();
        } else {
            if (userStageService.updateStageByCondition(branchId, groupId, stage, stageId, institute, grade, major, userIds, date))
                return Result.ok();
        }
        return Result.error().message("修改失败");
    }
}
