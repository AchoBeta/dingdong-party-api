package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.service.PartyStudentService;
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
    public ResponseEntity<Result<CommonItem<StudentEntity>>> queryById(@PathVariable String studentNo) {
        StudentEntity student = studentService.queryStudentById(studentNo);
        return CommonResult.success(CommonItem.restItem(student));
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
    public ResponseEntity<Result<CommonList<StudentEntity>>> query(@RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId,
                                                                   @RequestParam(value = "institute", required = false) String institute, @RequestParam(value = "grade", required = false) String grade,
                                                                   @RequestParam(value = "major", required = false) String major, @RequestParam(value = "className", required = false) String className,
                                                                   @RequestParam(value = "dormitoryArea", required = false) String dormitoryArea, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<StudentEntity> list = studentService.getList(branchId, groupId, institute, grade, major, className, dormitoryArea, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    /**
     * 创建学生
     */
    @PostMapping("")
    @ApiOperation("创建学生")
    public ResponseEntity<Result<IdVO>> create(@RequestBody StudentEntity studentEntity) {
        studentService.create(studentEntity);
        return CommonResult.success(new IdVO(studentEntity.getStudentId()));
    }

    /**
     * 更新学生
     */
    @PutMapping("/{studentId}")
    @ApiOperation("修改学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学号", type = "String", required = true)
    })
    public ResponseEntity<Result<IdVO>> update(@PathVariable("studentId") String studentId, @RequestBody StudentEntity studentEntity) {
        studentService.update(studentId, studentEntity);
        return CommonResult.success(new IdVO(studentId));
    }

    /**
     * 删除学生
     */
    @ApiOperation("删除学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentNo", value = "学号", type = "String", required = true)
    })
    @DeleteMapping("/{studentNo}")
    public ResponseEntity<Result<String>> remove(@PathVariable("studentNo") String studentNo) {
        return CommonResult.success("删除成功");
    }
}

