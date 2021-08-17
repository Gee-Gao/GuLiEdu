package com.gee.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gee.commonutils.R;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.Teacher;
import com.gee.edu.service.CourseService;
import com.gee.edu.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gee
 */
@RestController
@RequestMapping("eduservice/indexfront")
@CrossOrigin
@Slf4j
public class IndexFrontController {
    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    //查询前8条热门课程和前4条名师
    @ApiOperation("查询前8条热门课程和前4条名师")
    @GetMapping("index")
    @Cacheable(value = "CourseAndTeacher", key = "'Hot'")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<Course> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<Course> eduList = courseService.list(wrapperCourse);
        log.info("前8课程" + eduList);

        //查询前4条名师
        QueryWrapper<Teacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapperTeacher);
        log.info("前4名师" + teacherList);
        return R.ok().data("eduList", eduList).data("teacherList", teacherList);
    }
}
