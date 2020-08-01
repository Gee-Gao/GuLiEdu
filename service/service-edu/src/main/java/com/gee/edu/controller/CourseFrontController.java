package com.gee.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.chapter.ChapterVo;
import com.gee.edu.entity.vo.CourseFrontVo;
import com.gee.edu.entity.vo.CourseWebVo;
import com.gee.edu.service.ChapterService;
import com.gee.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Gee
 */
@RestController
@RequestMapping("eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Resource
    private CourseService courseService;
    @Resource
    private ChapterService chapterService;

    //课程详情
    @ApiOperation("课程详情")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@ApiParam("课程id") @PathVariable String courseId) {
        //根据课程id,编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.etChapterVideoByCourseId(courseId);
        return R.ok().data("course", courseWebVo).data("chapterVideoList", chapterVideoList);
    }

    //条件分页查询课程
    @ApiOperation("条件分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@ApiParam("页数") @PathVariable("page") Long page,
                                @ApiParam("每页记录数") @PathVariable("limit") Long limit,
                                @ApiParam("条件对象") @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getFrontCourseList(coursePage, courseFrontVo);
        return R.ok().data(map);
    }
}