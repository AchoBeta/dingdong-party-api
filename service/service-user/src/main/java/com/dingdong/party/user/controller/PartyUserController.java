package com.dingdong.party.user.controller;

import com.dingdong.party.serviceBase.common.api.*;
import com.dingdong.party.serviceBase.common.vo.IdVO;
import com.dingdong.party.serviceBase.utils.JwtUtils;
import com.dingdong.party.user.entity.PartyUser;
import com.dingdong.party.user.entity.vo.*;
import com.dingdong.party.user.service.PartyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author retraci
 * @since 2021-07-23
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/users")
public class PartyUserController {

    @Resource
    private PartyUserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get-by-branch")
    @ApiIgnore
    public List<String> queryByBranchId(@RequestParam String branchId) {
        return userService.queryByBranchId(branchId);
    }

    @ApiIgnore
    @GetMapping("/get-by-batch-groups")
    public List<String> queryUserByGroups(@RequestParam List<String> groups) {
        List<String> list = new ArrayList<>();
        for (String groupId : groups) {
            list.addAll(userService.queryUserByGroupId(groupId));
        }
        return list;
    }

    @ApiIgnore
    @GetMapping("/users/get-by-group")
    List<String> queryUserByGroup(String groupId) {
        return userService.queryUserByGroupId(groupId);
    }

    @ApiOperation("按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "党支部id", type = "String"), @ApiImplicitParam(name = "groupId", value = "党组id", type = "String"),
            @ApiImplicitParam(name = "stageId", value = "阶段id", type = "int"), @ApiImplicitParam(name = "periodsNum", value = "期数", type = "Integer"),
            @ApiImplicitParam(name = "institute", value = "学院名称", type = "String"), @ApiImplicitParam(name = "grade", value = "年级", type = "String"),
            @ApiImplicitParam(name = "major", value = "专业", type = "String"), @ApiImplicitParam(name = "page", value = "页号", type = "int"),
            @ApiImplicitParam(name = "size", value = "大小", type = "int")
    })
    @GetMapping("")
    public ResponseEntity<Result<CommonList<PartyUser>>> query(
            @RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "periodsNum", required = false) Integer periodsNum, @RequestParam(value = "stageId", required = false) Integer stageId,
            @RequestParam(value = "institute", required = false) String institute, @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "major", required = false) String major,
            @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<PartyUser> res = userService.getList(branchId, groupId, stageId, periodsNum, institute, grade, major, page, size);
        return CommonResult.success(CommonList.restList(res));
    }

    @ApiOperation("创建用户")
    @PostMapping("")
    public ResponseEntity<Result<IdVO>> create(@RequestBody PartyUser user) {
        userService.create(user);
        return CommonResult.success(new IdVO(user.getUserId()));
    }

    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> delete(@PathVariable String id) {
        userService.remove(id);
        return CommonResult.success("删除成功");
    }

    @ApiOperation("添加老师信息")
    @PostMapping("/teacher-info")
    public ResponseEntity<Result<String>> updateTeacher(@RequestBody TeacherEntity teacherEntity, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = (String) redisTemplate.opsForValue().get("user:" + token);
        userService.updateTeacher(userId, teacherEntity);
        return CommonResult.success("添加成功");
    }

    @ApiOperation("添加学生信息")
    @PostMapping("/student-info")
    public ResponseEntity<Result<String>> updateStudent(@RequestBody StudentEntity studentEntity, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = (String) redisTemplate.opsForValue().get("user:" + token);
        userService.updateStudent(userId, studentEntity);
        return CommonResult.success("添加成功");
    }

    @ApiOperation(value = "查看自己信息")
    @GetMapping("/info")
    public ResponseEntity<Result<CommonItem<UserInfoVO>>> info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = (String) redisTemplate.opsForValue().get("user:" + token);
        UserInfoVO info = userService.info(userId);
        return CommonResult.success(CommonItem.restItem(info));
    }

    @ApiOperation("查看用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, type = "String")
    })
    @GetMapping("/{userId}/info")
    public ResponseEntity<Result<CommonItem<UserInfoVO>>> info(@PathVariable String userId) {
        UserInfoVO info = userService.info(userId);
        return CommonResult.success(CommonItem.restItem(info));
    }

    @ApiOperation("查看用户的评论")
    @GetMapping("/{userId}/{comments}")
    public ResponseEntity<Result<CommonItems<CommentEntity>>> getComments(@PathVariable String comments, @PathVariable("userId") String userId) {
        List<CommentEntity> list = userService.getComments(userId);
        return CommonResult.success(CommonItems.restItems(list));
    }

    @ApiOperation("查看用户参与的活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true),
            @ApiImplicitParam(name = "status", value = "状态，3：未开始，4：正进行，5：已结束", type = "int"),
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String"),
    })
    @GetMapping("/{userId}/acitvities")
    public ResponseEntity<Result<CommonItems<UserActivityEntity>>> getActivities(@PathVariable("userId") String userId, @RequestParam(value = "status", required = false) Integer status,
                                                                                @RequestParam(value = "activityId", required = false) String activityId) {
        List<UserActivityEntity> list = userService.getActivities(userId, status, activityId);
        return CommonResult.success(CommonItems.restItems(list));
    }

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "微信开放id", required = true)
    })
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Result<LoginVO>> login(@RequestParam String openId) {
        PartyUser partyUser = userService.queryByOpenId(openId);
        if (partyUser == null) {
            // 不存在此用户，创建
            partyUser = new PartyUser();
            partyUser.setOpenId(openId);
            userService.save(partyUser);
        }
        String token = JwtUtils.generateToken(openId);
        redisTemplate.opsForValue().set("user:" + token, partyUser.getUserId(), 1440, TimeUnit.MINUTES);
        System.out.println(token);
        return CommonResult.success(new LoginVO(token, partyUser));
    }
}
