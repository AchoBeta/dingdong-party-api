package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.entity.PartyUserStage;
import com.dingdong.party.user.service.PartyUserStageService;
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
 * @since 2021-08-12
 */
@RestController
@Api(tags = "用户阶段管理")
@RequestMapping("/user-stages")
public class PartyUserStageController {

    @Resource
    PartyUserStageService userStageService;

    @ApiOperation("按 id 查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户阶段id", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyUserStage>>> queryById(@PathVariable("id") String id) {
        PartyUserStage stage = userStageService.queryById(id);
        return CommonResult.success(CommonItem.restItem(stage));
    }

    @ApiOperation("按条件查询用户阶段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id"), @ApiImplicitParam(name = "stage_id", value = "阶段id"),
            @ApiImplicitParam(name = "page", value = "页号"), @ApiImplicitParam(name = "size", value = "大小")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyUserStage>>> query(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "stage_id", required = false) Integer stageId,
                                                                    @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<PartyUserStage> list = userStageService.getList(userId, stageId, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建用户阶段信息")
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@RequestBody PartyUserStage userStage) {
        userStageService.create(userStage);
        return CommonResult.success(new IdVO(userStage.getId()));
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@RequestBody PartyUserStage userStage, @PathVariable("id") String id) {
        userStageService.update(userStage, id);
        return CommonResult.success(new IdVO(id));
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable("id") String id) {
        userStageService.remove(id);
        return CommonResult.success("删除成功");
    }
}

