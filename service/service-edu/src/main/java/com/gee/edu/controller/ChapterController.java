package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.entity.chapter.ChapterVo;
import com.gee.edu.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @ApiOperation("课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam("课程id") @PathVariable String courseId) {
        List<ChapterVo> list = chapterService.etChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }
}

