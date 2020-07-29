package com.gee.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.Teacher;
import com.gee.edu.service.CourseService;
import com.gee.edu.service.TeacherService;
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
@RequestMapping("eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;

    //分页查询讲师
    @ApiOperation("分页查询讲师")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }

    //讲师详情
    @ApiOperation("讲师详情")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@ApiParam("讲师id") @PathVariable String teacherId) {
        //根据讲师id查询讲师
        Teacher teacher = teacherService.getById(teacherId);
        //根据讲师id查询所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}