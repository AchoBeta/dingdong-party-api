package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.PartyActivity;
import com.dingdong.party.activity.entity.PartyActivityDetails;
import com.dingdong.party.activity.entity.vo.ActivityDetailsEntity;
import com.dingdong.party.activity.service.PartyActivityDetailsService;
import com.dingdong.party.activity.service.PartyActivityService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-16
 */
@RestController
@Api(tags = "活动管理")
@RequestMapping("/activities")
public class PartyActivityController {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    PartyActivityService activityService;

    @Resource
    PartyActivityDetailsService detailsService;

    @ApiOperation("根据id查找活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, type = "String")
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable String id) {
        PartyActivity activity = activityService.getById(id);
        System.out.println(activity);
        PartyActivityDetails details = detailsService.getById(id);
        if (activity != null) {
            ActivityDetailsEntity detailsEntity = new ActivityDetailsEntity();
            BeanUtils.copyProperties(activity, detailsEntity);
            BeanUtils.copyProperties(details, detailsEntity);
            System.out.println(detailsEntity);
            return Result.ok().data("item", detailsEntity);
        }
        return Result.error().message("查询失败");
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
    public Result query(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "startTime", required = false) String startTime,
                        @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "directorId", required = false) String directorId,
                        @RequestParam(value = "directorName", required = false) String directorName, @RequestParam(value = "status", required = false) Integer status,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<Object, Object> map = activityService.getList(name, branchId, startTime, endTime, directorId, directorName, status, page, size);
        if (map != null) return Result.ok().data("list", map);
        return Result.error().message("查无数据");
    }

    @ApiOperation("创建活动")
    @PostMapping("")
    public Result create(@RequestBody ActivityDetailsEntity activity, HttpServletRequest request) throws Exception {
        String activityId = activityService.create(activity);
        if (activityId != null) return Result.ok().data("activityId", activityId);
        return Result.error().message("创建失败");
    }

    @ApiOperation("删除活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, type = "String")
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id, HttpServletRequest request) {
        activityService.deleteById(id);
        return Result.ok();
    }

    @ApiOperation("修改活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, type = "String")
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody ActivityDetailsEntity detailsEntity, HttpServletRequest request) {
        detailsEntity.setId(id);
        activityService.modify(detailsEntity);
        return Result.ok().data("id", id);
    }


    @ApiOperation("活动提交审核")
    @PostMapping("/{activityId}/commit")
    public Result commit(@PathVariable("activityId") String activityId, HttpServletRequest request) {
        if (activityService.commit(activityId)) return Result.ok();
        return Result.error().message("提交失败");
    }

}

