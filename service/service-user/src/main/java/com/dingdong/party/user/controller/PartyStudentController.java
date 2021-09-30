package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.user.entity.PartyStudent;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.service.PartyStudentService;
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
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@Api(tags = "学生管理")
@RequestMapping("/students")
public class PartyStudentController {

    @Resource
    PartyStudentService studentService;

    @ApiOperation(value = "根据学号查学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentNo", value = "学号", type = "String", required = true)
    })
    @GetMapping("/{studentNo}")
    public Result queryById(@PathVariable String studentNo) {
        PartyStudent student = studentService.getById(studentNo);
        if (student != null) {
            StudentEntity studentEntity = new StudentEntity();
            BeanUtils.copyProperties(student, studentEntity);
            return Result.ok().data("student", studentEntity);
        }
        return Result.error().message("学生暂无记录");
    }

    @ApiOperation("按条件分页查询学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String"), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String"),
            @ApiImplicitParam(name = "institute", value = "学院", type = "String"), @ApiImplicitParam(name = "grade", value = "年级", type = "String"),
            @ApiImplicitParam(name = "major", value = "专业", type = "String"), @ApiImplicitParam(name = "className", value = "班级", type = "String"),
            @ApiImplicitParam(name = "dormitoryArea", value = "社区", type = "String"), @ApiImplicitParam(name = "page", value = "页号", type = "String"),
            @ApiImplicitParam(name = "size", value = "大小", type = "int"),
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId,
                        @RequestParam(value = "institute", required = false) String institute, @RequestParam(value = "grade", required = false) String grade,
                        @RequestParam(value = "major", required = false) String major, @RequestParam(value = "className", required = false) String className,
                        @RequestParam(value = "dormitoryArea", required = false) String dormitoryArea, @RequestParam("page") int page, @RequestParam("size") int size) {
        Map<Object, Object> list = studentService.getList(branchId, groupId, institute, grade, major, className, dormitoryArea , page, size);
        if (list != null) {
            return Result.ok().data("list", list);
        }
        return Result.error().message("查无数据");
    }

    /**
     * 创建学生
     */
    @PostMapping("")
    @ApiOperation("创建学生")
    public Result create(@RequestBody StudentEntity studentEntity) {
        PartyStudent student = new PartyStudent();
        BeanUtils.copyProperties(studentEntity, student);
        if (studentService.save(student)) {
            return Result.ok().data("studentNo", student.getStudentId());
        }
        return Result.error().message("创建用户失败");
    }

    /**
     * 更新学生
     */
    @PutMapping("/{studentId}")
    @ApiOperation("修改学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学号", type = "String", required = true)
    })
    public Result update(@PathVariable("studentId") String studentId, @RequestBody StudentEntity studentEntity) {
        PartyStudent student = new PartyStudent();
        BeanUtils.copyProperties(studentEntity, student);
        student.setStudentId(studentId);
        if (studentService.updateById(student)) {
            return Result.ok().data("studentNo", studentId);
        }
        return Result.error().message("更新用户失败");
    }

    /**
     * 删除学生
     */
    @ApiOperation("删除学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentNo", value = "学号", type = "String", required = true)
    })
    @DeleteMapping("/{studentNo}")
    public Result delete(@PathVariable("studentNo") String studentNo) {
        if (studentService.removeById(studentNo)) {
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败");
    }
}

