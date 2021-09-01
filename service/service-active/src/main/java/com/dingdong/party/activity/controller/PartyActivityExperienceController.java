package com.dingdong.party.activity.controller;


import com.dingdong.party.activity.entity.PartyActivityExperience;
import com.dingdong.party.activity.service.PartyActivityExperienceService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.serviceBase.exception.PartyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@Api(tags = "心得管理")
@RestController
@RequestMapping("/activities/{activityId}/experiences")
public class PartyActivityExperienceController {

    @Autowired
    PartyActivityExperienceService experienceService;

    @ApiOperation("根据id查询心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "心得id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {
        PartyActivityExperience experience = experienceService.getById(id);
        if (experience != null)
            return Result.ok().data("item", experience);
        return Result.error().message("查询失败");
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true), @ApiImplicitParam(name = "userId", value = "用户id", type = "String"),
            @ApiImplicitParam(name = "page", value = "页数", type = "int", required = true), @ApiImplicitParam(name = "size", value = "大小", type = "int", required = true),
    })
    @GetMapping("")
    public Result query(@PathVariable("activityId") String activityId, @RequestParam(value = "userId", required = false) String userId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<Object, Object> map = experienceService.getList(activityId, userId, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @ApiOperation("创建心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @PostMapping("")
    public Result create(@PathVariable("activityId") String activityId, @RequestBody PartyActivityExperience experience) {
        experience.setActivityId(activityId);
        if (experienceService.save(experience))
            return Result.ok().data("id", experience.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("更新心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "心得id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable("activityId") String acrivityId, @PathVariable("id") String id, @RequestBody PartyActivityExperience experience) {
        experience.setId(id);
        experience.setActivityId(acrivityId);
        if (experienceService.updateById(experience))
            return Result.ok().data("id", id);
        return Result.error().message("更新失败");
    }

    @ApiOperation("删除心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "心得id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {
        if (experienceService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

}

