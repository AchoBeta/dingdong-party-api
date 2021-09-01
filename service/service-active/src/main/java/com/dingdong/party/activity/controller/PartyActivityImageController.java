package com.dingdong.party.activity.controller;


import com.dingdong.party.activity.entity.PartyActivityImage;
import com.dingdong.party.activity.service.PartyActivityImageService;
import com.dingdong.party.commonUtils.result.Result;
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
@RestController
@Api(tags = "活动图片管理")
@RequestMapping("/activities/{activityId}/images")
public class PartyActivityImageController {

    @Autowired
    PartyActivityImageService imageService;

    @ApiOperation("根据id查询图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "图片id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("activityId") String activityId, @PathVariable String id) {
        PartyActivityImage image = imageService.getById(id);
        if (image != null)
            return Result.ok().data("item", image);
        return Result.error().message("查询失败");
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public Result query(@PathVariable("activityId") String activityId,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<Object, Object> map = imageService.getList(activityId, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @ApiOperation("创建活动图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @PostMapping("")
    public Result create(@PathVariable("activityId") String activityId, @RequestBody PartyActivityImage image) {
        image.setActivityId(activityId);
        if (imageService.save(image))
            return Result.ok().data("id", image.getId());
        return Result.error().message("查询失败");
    }

    @ApiOperation("更新图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "图片id", type = "String", required = true)
    })
    @PatchMapping("/{id}")
    public Result update(@PathVariable("activityId") String activityId, @PathVariable("id") String id, @RequestBody PartyActivityImage image) {
        image.setId(id);
        if (imageService.save(image))
            return Result.ok().data("id", id);
        return Result.error().message("更新失败");
    }

    @ApiOperation("删除图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "图片id", type = "String", required = true)
    })

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("activityId") String activityId, @PathVariable String id) {
        if (imageService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }
}

