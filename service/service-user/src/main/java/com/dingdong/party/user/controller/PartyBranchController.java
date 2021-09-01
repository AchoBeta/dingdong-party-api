package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyBranch;
import com.dingdong.party.user.service.PartyBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@Api(tags = "党支部管理")
@RequestMapping("/branches")
public class PartyBranchController {

    @Autowired
    PartyBranchService branchService;

    @ApiOperation("根据党支部id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "党支部id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable String id) {
        PartyBranch branch = branchService.getById(id);
        if (branch != null)
            return Result.ok().data("item", branch);
        return Result.error().message("查询失败");
    }

    @ApiOperation("按条件分页查询党支部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "党支部名称", type = "String"), @ApiImplicitParam(name = "parentId", value = "父级党支部id", type = "String"),
            @ApiImplicitParam(name = "parentName", value = "父级党支部名称", type = "String"), @ApiImplicitParam(name = "directorId", value = "负责人id", type = "String"),
            @ApiImplicitParam(name = "directorName", value = "负责人名称", type = "String"), @ApiImplicitParam(name = "page", value = "页号", type = "int"),
            @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "parentId", required = false) String parentId,
                        @RequestParam(value = "parentName", required = false) String parentName, @RequestParam(value = "directorId", required = false) String directorId,
                        @RequestParam(value = "directorName", required = false) String directorName, @RequestParam("page") Integer page,
                        @RequestParam("size") Integer size) {
        Map<Object, Object> map = branchService.getList(name, parentId, parentName, directorId, directorName, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @ApiOperation("创建党支部")
    @PostMapping("")
    public Result create(@RequestBody PartyBranch branch) {
        if (branchService.save(branch))
            return Result.ok().data("id", branch.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("删除党支部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "党支部id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable String id) {
        if (branchService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @ApiOperation("修改党支部id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "党支部id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody PartyBranch branch) {
        branch.setId(id);
        if (branchService.updateById(branch))
            return Result.ok().data("id", id);
        return Result.error().message("修改失败");
    }
}

