package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.user.entity.PartyGroup;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.service.PartyGroupService;
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
 * @since 2021-07-23
 */
@RestController
@Api(tags = "党组管理")
@RequestMapping("/branch/{branchId}/groups")
public class PartyGroupController {

    @Resource
    PartyGroupService groupService;

    @ApiOperation("根据党组id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "党组id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyGroup>>> queryById(@PathVariable("branchId") String branchId, @PathVariable("id") String id) {
        PartyGroup group = groupService.queryById(id);
        return CommonResult.success(CommonItem.restItem(group));
    }

    @ApiOperation("按条件分页查询党组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "党组名称", type = "String"), @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "branchName", value = "党支部名称", type = "String"), @ApiImplicitParam(name = "directorId", value = "负责人id", type = "String"),
            @ApiImplicitParam(name = "directorName", value = "负责人名称", type = "String"), @ApiImplicitParam(name = "page", value = "页号", type = "int"),
            @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyGroup>>> query(@RequestParam(value = "name", required = false) String name, @PathVariable(value = "branchId", required = false) String branchId,
                        @RequestParam(value = "branchName", required = false) String branchName, @RequestParam(value = "directorId", required = false) String directorId,
                        @RequestParam(value = "directorName", required = false) String directorName, @RequestParam("page") Integer page,
                        @RequestParam("size") Integer size) {
        List<PartyGroup> list = groupService.getList(name, branchId, branchName, directorId, directorName, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建党组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true)
    })
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@PathVariable("branchId") String branchId, @RequestBody PartyGroup group) {
        group.setBranchId(branchId);
        groupService.create(group);
        return CommonResult.success(new IdVO(group.getId()));
    }

    @ApiOperation("删除党组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "党组id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable("branchId") String branchId, @PathVariable("id") String id) {
        groupService.remove(id);
        return CommonResult.success("删除成功");
    }

    @ApiOperation("修改党组id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "党组id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable("branchId") String branchId, @PathVariable("id") String id, @RequestBody PartyGroup group) {
        group.setBranchId(branchId);
        group.setId(id);
        return CommonResult.success(new IdVO(id));
    }
}

