package com.gee.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.edu.entity.Comment;
import com.gee.edu.service.CommentService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/edu/comment")
@Slf4j
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("{page}/{limit}")
    public R pageQueryCommentCondition(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象") String courseId) {
        Page<Comment> pageParam = new Page<>(page, limit);
        commentService.page(pageParam, new LambdaQueryWrapper<Comment>()
                .eq(!StringUtils.isEmpty(courseId), Comment::getCourseId, courseId)
                .orderByDesc(Comment::getGmtModified));
        log.info("评论列表" + pageParam.getRecords());
        return R.ok().list(pageParam);
    }

    @DeleteMapping
    public R deleteCommon(@PathVariable String id) {
        log.info("评论id" + id);
        commentService.removeById(id);
        return R.ok();
    }

}

