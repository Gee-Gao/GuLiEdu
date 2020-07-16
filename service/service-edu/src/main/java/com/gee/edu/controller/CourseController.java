package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.vo.CourseInfoVo;
import com.gee.edu.entity.vo.CoursePublishVo;
import com.gee.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class CourseController {
    @Resource
    private CourseService courseService;

    //添加课程基本信息
    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam("课程信息对象") @RequestBody CourseInfoVo courseInfoVo) {
        //返回添加课程的id,方便添加课程大纲
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据课程id查询课程基本信息
    @ApiOperation("根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@ApiParam("课程id") @PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@ApiParam("课程信息对象") @RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@ApiParam("课程id") @PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布，修改课程状态
    @ApiOperation("课程最终发布，修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@ApiParam("课程id") @PathVariable("id") String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }
}

