package com.dingdong.party.admin.controller;


import com.dingdong.party.admin.entity.PartyFaculty;
import com.dingdong.party.admin.service.PartyFacultyService;
import com.dingdong.party.commonUtils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
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

}

