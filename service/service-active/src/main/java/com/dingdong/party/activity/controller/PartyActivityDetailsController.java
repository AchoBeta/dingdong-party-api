package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.PartyActivityDetails;
import com.dingdong.party.activity.service.PartyActivityDetailsService;
import com.dingdong.party.commonUtils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@RestController
@Api(tags = "活动细节管理")
@RequestMapping("/activities/{activityId}")
public class PartyActivityDetailsController {

    @Resource
    PartyActivityDetailsService detailsService;

    @GetMapping("show-detail")
    @ApiOperation("展示活动细节")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    public Result queryByActivityId(@PathVariable String activityId) {
        PartyActivityDetails details = detailsService.getById(activityId);
        if (details != null) return Result.ok().data("item", details);
        return Result.error().message("获取失败");
    }

}

