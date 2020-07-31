package com.gee.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.vo.CourseFrontVo;
import com.gee.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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