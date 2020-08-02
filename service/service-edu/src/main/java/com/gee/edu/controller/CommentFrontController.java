package com.gee.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.JwtUtils;
import com.gee.commonutils.R;
import com.gee.edu.client.UserClient;
import com.gee.edu.entity.Comment;
import com.gee.edu.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class CommentFrontController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserClient userClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "根据课程id查询评论列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象") String courseId) {

        //分页查询
        Page<Comment> pageParam = new Page<>(page, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        //根据评论时间进行降序查询
        wrapper.orderByDesc("gmt_create");
        commentService.page(pageParam, wrapper);
        List<Comment> commentList = pageParam.getRecords();
        //分页查询数据进行封装并返回
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    //添加评论
    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody Comment comment, HttpServletRequest request) {
        //通过token 获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //如果token不存在，让用户进行登录
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }

        //用户登录，设置id
        comment.setMemberId(memberId);
        //远程调用service-user模块，获取用户信息
        R r = userClient.getUserInfoComment(memberId);
        Map<String, Object> data = r.getData();
        String memberStr = data.get("member").toString();
        String[] split = memberStr.split(",");
        //设置用户昵称和头像
        comment.setNickname(split[4].split("=")[1]);
        comment.setAvatar(split[7].split("=")[1]);
        //保存到数据库
        commentService.save(comment);
        return R.ok();
    }
}