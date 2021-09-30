package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyTeacher;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import com.dingdong.party.user.service.PartyTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@Api(tags = "教师管理")
@RequestMapping("/instructors")
public class PartyTeacherController {

    @Resource
    PartyTeacherService teacherService;

    @ApiOperation("根据id查询老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "老师id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable String id) {
        PartyTeacher teacher = teacherService.getById(id);
        if (teacher != null) {
            TeacherEntity teacherEntity = new TeacherEntity();
            BeanUtils.copyProperties(teacher, teacherEntity);
            return Result.ok().data("item", teacherEntity);
        }
        return Result.error().message("查无此记录");
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "老师姓名", type = "String"), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String"),
            @ApiImplicitParam(name = "groupName", value = "党组名称", type = "String"), @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String"),
            @ApiImplicitParam(name = "branchName", value = "党支部名称", type = "String"), @ApiImplicitParam(name = "partyPosition", value = "党内职务", type = "String"),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "groupId", required = false) String groupId,
                        @RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "branchId", required = false) String branchId,
                        @RequestParam(value = "branchName", required = false) String branchName, @RequestParam(value = "partyPosition", required = false) String partyPosition,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<Object, Object> map = teacherService.getList(name, groupId, groupName, branchId, branchName, partyPosition, page, size);
        if (map != null) {
            return Result.ok().data("list", map);
        }
        return Result.error().message("查询失败");
    }

    @ApiOperation("增加老师")
    @PostMapping("")
    public Result create(@RequestBody TeacherEntity teacherEntity) {
        PartyTeacher teacher = new PartyTeacher();
        BeanUtils.copyProperties(teacherEntity, teacher);
        if (teacherService.save(teacher)) {
            return Result.ok().data("id", teacher.getTeacherId());
        }
        return Result.error().message("增加失败");
    }

    @ApiOperation("修改老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "老师id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody TeacherEntity teacherEntity) {
        PartyTeacher teacher = new PartyTeacher();
        BeanUtils.copyProperties(teacherEntity, teacher);
        teacher.setTeacherId(id);
        if (teacherService.updateById(teacher)) {
            return Result.ok().data("id", id);
        }
        return Result.error().message("删除失败");
    }

    @ApiOperation("删除老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "老师id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        if (teacherService.removeById(id)) {
            return Result.ok();
        }
        return Result.error().message("删除失败");
    }

}

