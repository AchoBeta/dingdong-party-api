package com.dingdong.party.user.controller;

import com.dingdong.party.commonUtils.result.Result;
import com.dingdong.party.serviceBase.utils.JwtUtils;
import com.dingdong.party.user.entity.PartyUser;
import com.dingdong.party.user.entity.vo.CommentEntity;
import com.dingdong.party.user.entity.vo.StudentEntity;
import com.dingdong.party.user.entity.vo.TeacherEntity;
import com.dingdong.party.user.service.PartyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
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
@Api(tags = "用户管理")
@RequestMapping("/users")
public class PartyUserController {

    @Autowired
    PartyUserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    JwtUtils jwtUtils = new JwtUtils();

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
    public Result query(
                        @RequestParam(value = "branchId", required = false) String branchId, @RequestParam(value = "groupId", required = false) String groupId,
                        @RequestParam(value = "periodsNum", required = false) Integer periodsNum, @RequestParam(value = "stageId", required = false) Integer stageId,
                        @RequestParam(value = "institute", required = false) String institute, @RequestParam(value = "grade", required = false) String grade,
                        @RequestParam(value = "major", required = false) String major,
                        @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = userService.getList(branchId, groupId, stageId, periodsNum, institute, grade, major, page, size);
        if (map != null)
            return Result.ok().data("list", map);
        return Result.error().message("查询失败");
    }


    @ApiOperation("创建用户")
    @PostMapping("")
    public Result create(@RequestBody PartyUser user) {
        if (userService.save(user))
            return Result.ok().data("id", user.getUserId());
        return Result.error().message("创建失败");
    }

    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", type = "String", required = true)
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        if (userService.removeById(id))
            return Result.ok();
        return Result.error().message("删除失败");
    }

    @ApiOperation("添加老师信息")
    @PostMapping("/teacher-info")
    public Result updateTeacher(@RequestBody TeacherEntity teacherEntity, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = (String) redisTemplate.opsForValue().get("user:" + token);
        userService.updateTeacher(userId, teacherEntity);
        return Result.ok();
    }

    @ApiOperation("添加学生信息")
    @PostMapping("/student-info")
    public Result updateStudent(@RequestBody StudentEntity studentEntity, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = (String) redisTemplate.opsForValue().get("user:" + token);
        userService.updateStudent(userId, studentEntity);
        return Result.ok();
    }

    @ApiOperation(value = "查看自己信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = (String) redisTemplate.opsForValue().get("user:" + token);
        Object info = userService.info(userId);
        if (info != null)
            return Result.ok().data("item", info);
        return Result.error().message("查询失败");
    }

    @ApiOperation("查看用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, type = "String")
    })
    @GetMapping("/{userId}/info")
    public Result info(@PathVariable String userId) {
        Map<String, Object> info = userService.info(userId);
        if (info != null)
            return Result.ok().data("item", info);
        return Result.error().message("查询失败");
    }

    @ApiOperation("查看用户的评论")
    @GetMapping("/{userId}/{comments}")
    public Result getComments(@PathVariable("userId") String userId) {
        List<CommentEntity> list = userService.getComments(userId);
        if (list != null)
            return Result.ok().data("items", list).data("total", list.size());
        return Result.error().message("暂无记录");
    }

    @ApiOperation("查看用户参与的活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", type = "String", required = true),
            @ApiImplicitParam(name = "status", value = "状态，3：未开始，4：正进行，5：已结束", type = "int"),
            @ApiImplicitParam(name = "activityId", value = "活动id", type = "String"),
    })
    @GetMapping("/{userId}/acitvities")
    public Result getActivities(@PathVariable("userId") String userId, @RequestParam(value = "status", required = false) Integer status,
                                @RequestParam(value = "activityId", required = false) String activityId) {
        Object list = userService.getActivities(userId, status, activityId);
        if (list != null)
            return Result.ok().data("items", list);
        return Result.error().message("查询失败");
    }

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "微信开放id", required = true)
    })
    @PostMapping("/login")
    public Result login(@RequestParam String openId) {
        PartyUser partyUser = userService.queryByOpenId(openId);
        if (partyUser == null) {
            // 不存在此用户，创建
            partyUser = new PartyUser();
            partyUser.setOpenId(openId);
            userService.save(partyUser);
        }
        String token = jwtUtils.generateToken(openId);
        redisTemplate.opsForValue().set("user:" + token, partyUser.getUserId(), 1440, TimeUnit.MINUTES);
        System.out.println(token);
        return Result.ok().data("token", token).data("item", partyUser);
    }
}