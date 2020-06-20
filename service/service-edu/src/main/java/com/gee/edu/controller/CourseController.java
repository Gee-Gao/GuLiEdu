package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.entity.vo.CourseInfoVo;
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
        return R.ok().data("courseId",id);
    }


}

