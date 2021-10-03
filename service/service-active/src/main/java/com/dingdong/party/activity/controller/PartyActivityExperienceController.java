package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.PartyActivityComment;
import com.dingdong.party.activity.entity.PartyActivityExperience;
import com.dingdong.party.activity.service.PartyActivityExperienceService;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.vo.IdVO;
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
 * 前端控制器
 * </p>
 *
 * @author retraci
 * @since 2021-07-16
 */
@Api(tags = "心得管理")
@RestController
@RequestMapping("/activities/{activityId}/experiences")
public class PartyActivityExperienceController {

    @Resource
    PartyActivityExperienceService experienceService;

    @ApiOperation("根据id查询心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "心得id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyActivityExperience>>> queryById(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {
        PartyActivityExperience experience = experienceService.queryById(id);
        return CommonResult.success(CommonItem.restItem(experience));
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true), @ApiImplicitParam(name = "userId", value = "用户id", type = "String"),
            @ApiImplicitParam(name = "page", value = "页数", type = "int", required = true), @ApiImplicitParam(name = "size", value = "大小", type = "int", required = true),
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyActivityExperience>>> query(@PathVariable("activityId") String activityId, @RequestParam(value = "userId", required = false) String userId,
                                                                             @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<PartyActivityExperience> list = experienceService.getList(activityId, userId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@PathVariable("activityId") String activityId, @RequestBody PartyActivityExperience experience) {
        experienceService.create(activityId, experience);
        return CommonResult.success(new IdVO(experience.getId()));
    }

    @ApiOperation("更新心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "心得id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable("activityId") String activityId, @PathVariable("id") String id, @RequestBody PartyActivityExperience experience) {
        experienceService.update(activityId, id, experience);
        return CommonResult.success(new IdVO(id));
    }

    @ApiOperation("删除心得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "心得id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> delete(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {
        experienceService.remove(id);
        return CommonResult.success("删除成功");
    }

}

