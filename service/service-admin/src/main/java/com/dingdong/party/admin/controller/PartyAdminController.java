package com.dingdong.party.admin.controller;

import com.dingdong.party.admin.entity.PartyAdmin;
import com.dingdong.party.admin.entity.vo.AdminEntity;
import com.dingdong.party.admin.service.PartyAdminService;
import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.commonUtils.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-17
 */
@Api(tags = "管理员管理")
@RestController
@RequestMapping("/admins")
public class PartyAdminController extends BaseController {

    @Autowired
    PartyAdminService adminService;


    @ApiOperation("根据id查询管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", type = "String", required = true)
    })
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") String id) {
        PartyAdmin admin = adminService.getById(id);
        if (admin != null) {
            Object o = adminService.info(admin.getUserId());
            return Result.ok().data("item", o);
        }
        return Result.error().message("查询失败");
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "String", required = true), @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "authority", value = "权限", type = "Integer"), @ApiImplicitParam(name = "page", value = "页号", type = "Integer", required = true),
            @ApiImplicitParam(name = "size", value = "页数", type = "Integer", required = true)
    })
    @GetMapping("")
    public Result query(@RequestParam(value = "groupId", required = false) String groupId, @RequestParam(value = "branchId", required = false) String branchId,
                        @RequestParam(value = "authority", required = false) Integer authority, @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size") Integer size) {
        Map<String, Object> list = adminService.getList(groupId, branchId, authority, page, size);
        if (list != null)
            return Result.ok().data("list", list);
        return Result.error().message("查无记录");
    }

    @ApiOperation("删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        if (adminService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @ApiOperation("创建管理员")
    @PostMapping("")
    public Result create(@RequestBody PartyAdmin admin) {
        if (adminService.create(admin))
            return Result.ok().data("id", admin.getId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("修改管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", type = "String", required = true)
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody AdminEntity adminEntity) {
        PartyAdmin admin = new PartyAdmin();
        BeanUtils.copyProperties(adminEntity, admin);
        admin.setId(id);
        if (adminService.updateById(admin))
            return Result.ok().data("id", id);
        return Result.error().message("修改失败");
    }


    @ApiOperation("修改密码")
    @PostMapping("/{adminId}/change-pwd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id", type = "String", required = true),
            @ApiImplicitParam(name = "oldPwd", value = "旧密码", type = "String", required = true),
            @ApiImplicitParam(name = "newPwd", value = "新密码", type = "String", required = true),
    })
    public Result changePassword(@PathVariable("adminId") String adminId, @RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd) {
        if (adminService.changePassword(adminId, oldPwd, newPwd)) {
            return Result.ok();
        }
        return Result.error().message("密码错误");
    }

    @ApiOperation("用户基本信息审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true), @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "String", required = true),
            @ApiImplicitParam(name = "status", value = "审核状态（1：通过，2：未通过）", type = "int", required = true), @ApiImplicitParam(name = "status", value = "审核不通过原因", type = "String")
    })
    @PostMapping("/examine-info-success")
    public Result examineUserInfo(@RequestParam("userId") String userId, @RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId, HttpServletRequest request,
                                  @RequestParam("status") Integer status, @RequestParam(value = "statusReason", required = false) String statusReason) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (status == 2 && !"".equals(statusReason)) {
                return Result.error().message("请填写驳回原因");
            }
            if (adminService.updateUserById(userId, status, statusReason))
                return Result.ok();
            return Result.error().message("审核失败");
        }else {
            return Result.error(ResultCode.NO_PERMISSION);
        }
    }

    @ApiOperation("活动审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String", required = true),
            @ApiImplicitParam(name = "groupId", value = "党支部id数组", type = "String", required = true)
    })
    @PostMapping("examine-activity")
    public Result examineActivity(@RequestParam("activityId") String activityId, @RequestParam(value = "groupId", required = false) String groupId,
                                  @RequestParam(value = "branchId", required = false) String branchId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (adminService.examineActivity(activityId))
                return Result.ok();
            return Result.error().message("审核失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("活动审核驳回")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String", required = true), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String", required = true),
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String")
    })
    @PostMapping("reject-activity")
    public Result rejectActivity(@RequestParam("activityId") String activityId, @RequestParam(value = "groupId", required = false) String groupId,
                                 @RequestParam(value = "branchId",required = false) String branchId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (adminService.rejectActivity(activityId))
                return Result.ok();
            return Result.error().message("审核失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("用户活动请假通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "string", required = true), @ApiImplicitParam(name = "activityId", value = "活动id", type = "string", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "string", required = true)
    })
    @PostMapping("/partake-activity-success")
    public Result partakeActivitySuccess(@RequestParam("userId") String userId, @RequestParam("activityId") String activityId,
                                         @RequestParam(value = "groupId", required = false) String groupId, @RequestParam(value = "branchId", required = false) String branchId,
                                         HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (adminService.partakeActivitySuccess(userId, activityId))
                return Result.ok();
            return Result.error().message("审批失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("用户活动请假失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "string", required = true), @ApiImplicitParam(name = "activityId", value = "活动id", type = "string", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "string", required = true)
    })
    @PostMapping("/partake-activity-fail")
    public Result partakeActivityFail(@RequestParam("userId") String userId, @RequestParam("activityId") String activityId,
                                      @RequestParam(value = "groupId", required = false) String groupId, @RequestParam(value = "branchId", required = false) String branchId,
                                      HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (adminService.partakeActivityFail(userId, activityId))
                return Result.ok();
            return Result.error().message("审批失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);

    }

    @ApiOperation("用户参与活动成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "string", required = true), @ApiImplicitParam(name = "activityId", value = "活动id", type = "string", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "string")
    })
    @PostMapping("/participate-activity-success")
    public Result participateActivitySuccess(@RequestParam("userId") String userId, @RequestParam("activityId") String activityId,
                                             @RequestParam(value = "groupId", required = false) String groupId, @RequestParam(value = "branchId", required = false) String branchId,
                                             HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (adminService.participateActivitySuccess(userId, activityId))
                return Result.ok();
            return Result.error().message("审批失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("用户参与活动失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "string", required = true), @ApiImplicitParam(name = "activityId", value = "活动id", type = "string", required = true),
            @ApiImplicitParam(name = "groupId", value = "党组id", type = "string", required = true)
    })
    @PostMapping("/participate-activity-fail")
    public Result participateActivityFail(@RequestParam("userId") String userId, @RequestParam("activityId") String activityId,
                                             @RequestParam(value = "groupId", required = false) String groupId, @RequestParam(value = "branchId", required = false) String branchId,
                                          HttpServletRequest request) {
        String token = request.getHeader("token");
        if (getGroupAdminAuthority(token, groupId) > 0 || getBranchAdminAuthority(token, branchId) > 0) {
            if (adminService.participateActivityFail(userId, activityId))
                return Result.ok();
            return Result.error().message("审批失败");
        }
        return Result.error(ResultCode.NO_PERMISSION);
    }

}



