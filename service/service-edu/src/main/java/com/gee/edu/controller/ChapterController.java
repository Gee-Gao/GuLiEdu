package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.entity.Chapter;
import com.gee.edu.entity.chapter.ChapterVo;
import com.gee.edu.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @ApiOperation("课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam("课程id") @PathVariable String courseId) {
        log.info("课程id:{}", courseId);
        List<ChapterVo> list = chapterService.etChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    //添加章节
    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@ApiParam("课程对象") @RequestBody Chapter chapter) {
        log.info("章节对象" + chapter);
        chapterService.save(chapter);
        return R.ok();
    }

    //根据章节id查询
    @ApiOperation("根据章节id查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@ApiParam("课程id") @PathVariable String chapterId) {
        log.info("章节id:{}", chapterId);
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }


    //修改章节
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@ApiParam("课程对象") @RequestBody Chapter chapter) {
        log.info("章节对象" + chapter);
        chapterService.updateById(chapter);
        return R.ok();
    }

    //删除章节
    @ApiOperation("删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapterInfo(@ApiParam("课程id") @PathVariable String chapterId) {
        log.info("章节id:{}", chapterId);
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

