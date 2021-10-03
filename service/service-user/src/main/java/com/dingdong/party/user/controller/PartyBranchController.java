package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.user.entity.PartyBranch;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.service.PartyBranchService;
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
@Api(tags = "党支部管理")
@RequestMapping("/branches")
public class PartyBranchController {

    @Resource
    PartyBranchService branchService;

    @ApiOperation("根据党支部id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "党支部id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonItem<PartyBranch>>> queryById(@PathVariable String id) {
        PartyBranch branch = branchService.queryById(id);
        return CommonResult.success(CommonItem.restItem(branch));
    }

    @ApiOperation("按条件分页查询党支部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "党支部名称", type = "String"), @ApiImplicitParam(name = "parentId", value = "父级党支部id", type = "String"),
            @ApiImplicitParam(name = "parentName", value = "父级党支部名称", type = "String"), @ApiImplicitParam(name = "directorId", value = "负责人id", type = "String"),
            @ApiImplicitParam(name = "directorName", value = "负责人名称", type = "String"), @ApiImplicitParam(name = "page", value = "页号", type = "int"),
            @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyBranch>>> query(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "parentId", required = false) String parentId,
                                                                 @RequestParam(value = "parentName", required = false) String parentName, @RequestParam(value = "directorId", required = false) String directorId,
                                                                 @RequestParam(value = "directorName", required = false) String directorName, @RequestParam("page") Integer page,
                                                                 @RequestParam("size") Integer size) {
        List<PartyBranch> list = branchService.getList(name, parentId, parentName, directorId, directorName, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("创建党支部")
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@RequestBody PartyBranch branch) {
        branchService.create(branch);
        return CommonResult.success(new IdVO(branch.getId()));
    }

    @ApiOperation("删除党支部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "党支部id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable String id) {
        branchService.remove(id);
        return CommonResult.success("删除成功");
    }

    @ApiOperation("修改党支部id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "党支部id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable String id, @RequestBody PartyBranch branch) {
        branch.setId(id);
        branchService.update(branch);
        return CommonResult.success(new IdVO(id));
    }
}

