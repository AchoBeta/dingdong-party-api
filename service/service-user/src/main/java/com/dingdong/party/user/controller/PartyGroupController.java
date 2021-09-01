package com.dingdong.party.user.controller;


import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyGroup;
import com.dingdong.party.user.service.PartyGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@Api(tags = "党组管理")
@RequestMapping("/branch/{branchId}/groups")
public class PartyGroupController {

    @Autowired
    PartyGroupService groupService;

    @ApiOperation("根据党组id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "党组id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("branchId") String branchId, @PathVariable("id") String id) {
        PartyGroup group = groupService.getById(id);
        if (group != null) {
            return Result.ok().data("item", group);
        }
        return Result.error().message("查询失败");
    }

    @ApiOperation("按条件分页查询党组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "党组名称", type = "String"), @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "branchName", value = "党支部名称", type = "String"), @ApiImplicitParam(name = "directorId", value = "负责人id", type = "String"),
            @ApiImplicitParam(name = "directorName", value = "负责人名称", type = "String"), @ApiImplicitParam(name = "page", value = "页号", type = "int"),
            @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "name", required = false) String name, @PathVariable(value = "branchId", required = false) String branchId,
                        @RequestParam(value = "branchName", required = false) String branchName, @RequestParam(value = "directorId", required = false) String directorId,
                        @RequestParam(value = "directorName", required = false) String directorName, @RequestParam("page") Integer page,
                        @RequestParam("size") Integer size) {
        Map<Object, Object> map = groupService.getList(name, branchId, branchName, directorId, directorName, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }

    @ApiOperation("创建党组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true)
    })
    @PostMapping("")
    public Result create(@PathVariable("branchId") String branchId, @RequestBody PartyGroup group) {
        group.setBranchId(branchId);
        if (groupService.save(group))
            return Result.ok().data("id", group.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("删除党组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "党组id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("branchId") String branchId, @PathVariable("id") String id) {
        if (groupService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @ApiOperation("修改党组id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "id", value = "党组id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable("branchId") String branchId, @PathVariable("id") String id, @RequestBody PartyGroup group) {
        group.setBranchId(branchId);
        group.setId(id);
        if (groupService.updateById(group))
            return Result.ok().data("id", id);
        return Result.error().message("修改失败");
    }
}

