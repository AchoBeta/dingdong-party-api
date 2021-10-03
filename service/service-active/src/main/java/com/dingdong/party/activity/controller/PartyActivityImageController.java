package com.dingdong.party.activity.controller;

import com.dingdong.party.activity.entity.PartyActivityImage;
import com.dingdong.party.activity.service.PartyActivityImageService;

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
@Api(tags = "活动图片管理")
@RequestMapping("/activities/{activityId}/images")
public class PartyActivityImageController {

    @Resource
    PartyActivityImageService imageService;

    @ApiOperation("根据id查询图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "图片id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyActivityImage>>> queryById(@PathVariable("activityId") String activityId, @PathVariable String id) {
        PartyActivityImage image = imageService.queryById(id);
        return CommonResult.success(CommonItem.restItem(image));
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyActivityImage>>> query(@PathVariable("activityId") String activityId,
                                                                   @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<PartyActivityImage> list = imageService.getList(activityId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建活动图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true)
    })
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@PathVariable("activityId") String activityId, @RequestBody PartyActivityImage image) {
        imageService.create(activityId, image);
        return CommonResult.success(new IdVO(image.getId()));
    }

    @ApiOperation("更新图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "图片id", type = "String", required = true)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable("activityId") String activityId, @PathVariable("id") String id, @RequestBody PartyActivityImage image) {
        imageService.update(activityId, id, image);
        return CommonResult.success(new IdVO(id));
    }

    @ApiOperation("删除图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "图片id", type = "String", required = true)
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable("activityId") String activityId, @PathVariable String id) {
        imageService.remove(id);
        return CommonResult.success("删除成功");
    }

}

