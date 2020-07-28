package com.gee.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.edu.entity.Teacher;
import com.gee.edu.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    //分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }
}
