package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.PartyActivity;
import com.dingdong.party.activity.entity.vo.ActivityDetailsEntity;
import com.dingdong.party.activity.service.PartyActivityDetailsService;
import com.dingdong.party.activity.service.PartyActivityService;

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
@RestController
@Api(tags = "活动管理")
@RequestMapping("/activities")
public class PartyActivityController {

    @Resource
    PartyActivityService activityService;

    @Resource
    PartyActivityDetailsService detailsService;

    @ApiOperation("根据id查找活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, type = "String")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<ActivityDetailsEntity>>> queryById(@PathVariable String id) {
        ActivityDetailsEntity activity = activityService.queryById(id);
        return CommonResult.success(CommonItem.restItem(activity));
    }

    @ApiOperation("按条件分页查询活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "活动名称", type = "String"), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String"),
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String"), @ApiImplicitParam(name = "startTime", value = "起始时间(在此之后都行)", type = "String"),
            @ApiImplicitParam(name = "endTime", value = "截至时间(在此之前)", type = "String"), @ApiImplicitParam(name = "directorId", value = "负责人", type = "String"),
            @ApiImplicitParam(name = "directorName", value = "负责人名称", type = "String"), @ApiImplicitParam(name = "status", value = "审批状态(0未审批 1已审批 2驳回)", type = "int"),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyActivity>>> query(@RequestParam(value = "name", required = false) String name,
                                                                   @RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "startTime", required = false) String startTime,
                                                                   @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "directorId", required = false) String directorId,
                                                                   @RequestParam(value = "directorName", required = false) String directorName, @RequestParam(value = "status", required = false) Integer status,
                                                                   @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<PartyActivity> list = activityService.getList(name, branchId, startTime, endTime, directorId, directorName, status, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建活动")
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@RequestBody ActivityDetailsEntity activity) {
        activityService.create(activity);
        return CommonResult.success(new IdVO(activity.getId()));
    }

    @ApiOperation("删除活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, type = "String")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable String id) {
        activityService.remove(id);
        return CommonResult.success("删除成功");
    }

    @ApiOperation("修改活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, type = "String")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable String id, @RequestBody ActivityDetailsEntity detailsEntity) {
        activityService.update(id, detailsEntity);
        return CommonResult.success(new IdVO(id));
    }


    @ApiOperation("活动提交审核")
    @PostMapping("/{activityId}/commit")
    public ResponseEntity<Result<String>> commit(@PathVariable("activityId") String activityId) {
        activityService.commit(activityId);
        return CommonResult.success("提交成功");
    }

}

