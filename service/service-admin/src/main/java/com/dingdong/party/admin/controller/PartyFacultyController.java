package com.dingdong.party.admin.controller;

import com.dingdong.party.admin.entity.PartyFaculty;
import com.dingdong.party.admin.service.PartyFacultyService;
import com.dingdong.party.commonUtils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-08
 */
@RestController
@Api(tags = "院系信息")
@RequestMapping("/institutes")
public class PartyFacultyController {

    @Resource
    PartyFacultyService facultyService;

    @ApiOperation("获取全部学院信息")
    @GetMapping("")
    public Result queryInstitutes () {
        List<PartyFaculty> list = facultyService.query("0");
        if (list != null)
            return Result.ok().data("total", list.size()).data("list", list);
        return Result.error().message("没有记录");
    }

    @ApiOperation("获取学院下的全部专业")
    @GetMapping("/{instituteId}/major")
    public Result queryMajorByInstituteId(@PathVariable("instituteId") String instituteId) {
        List<PartyFaculty> list = facultyService.query(instituteId);
        if (list != null)
            return Result.ok().data("total", list.size()).data("list", list);
        return Result.error().message("暂无记录");
    }

    @ApiOperation("删除学院 / 专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "学院 / 专业id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        if (facultyService.remove(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @ApiOperation("创建学院 / 专业")
    @PostMapping("")
    public Result create(@RequestBody PartyFaculty partyFaculty) {
        if (facultyService.create(partyFaculty))
            return Result.ok().data("id", partyFaculty.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("修改学院 / 专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "学院 / 专业id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody PartyFaculty partyFaculty) {
        PartyFaculty faculty = new PartyFaculty();
        BeanUtils.copyProperties(partyFaculty, faculty);
        faculty.setId(id);
        if (facultyService.updateById(faculty))
            return Result.ok().data("id", id);
        return Result.error().message("修改失败");
    }

}

