package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.CommonItem;
import com.dingdong.party.serviceBase.common.api.CommonList;
import com.dingdong.party.serviceBase.common.api.CommonResult;
import com.dingdong.party.serviceBase.common.api.Result;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.user.entity.PartyTeacher;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import com.dingdong.party.user.service.PartyTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.statement.Commit;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author retraci
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
    public ResponseEntity<Result<CommonItem<TeacherEntity>>> queryById(@PathVariable String id) {
        TeacherEntity teacher = teacherService.queryById(id);
        return CommonResult.success(CommonItem.restItem(teacher));
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "老师姓名", type = "String"), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String"),
            @ApiImplicitParam(name = "groupName", value = "党组名称", type = "String"), @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String"),
            @ApiImplicitParam(name = "branchName", value = "党支部名称", type = "String"), @ApiImplicitParam(name = "partyPosition", value = "党内职务", type = "String"),
            @ApiImplicitParam(name = "page", value = "页号", type = "int"), @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<TeacherEntity>>> query(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "groupId", required = false) String groupId,
                                                                   @RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "branchId", required = false) String branchId,
                                                                   @RequestParam(value = "branchName", required = false) String branchName, @RequestParam(value = "partyPosition", required = false) String partyPosition,
                                                                   @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<TeacherEntity> list = teacherService.getList(name, groupId, groupName, branchId, branchName, partyPosition, page, size);
        return CommonResult.success(CommonList.restList(list));
    }

    @ApiOperation("增加老师")
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@RequestBody TeacherEntity teacherEntity) {
        teacherService.create(teacherEntity);
        return CommonResult.success(new IdVO(teacherEntity.getTeacherId()));
    }

    @ApiOperation("修改老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "老师id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<IdVO>> update(@PathVariable String id, @RequestBody TeacherEntity teacherEntity) {
        teacherService.update(id, teacherEntity);
        return CommonResult.success(new IdVO(id));
    }

    @ApiOperation("删除老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "老师id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> remove(@PathVariable String id) {
        teacherService.remove(id);
        return CommonResult.success("删除成功");
    }

}

