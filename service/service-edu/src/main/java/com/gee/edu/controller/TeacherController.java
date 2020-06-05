package com.gee.edu.controller;


import com.gee.edu.entity.Teacher;
import com.gee.edu.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @GetMapping("findAll")
    public List<Teacher> findAllTeacher(){
        return teacherService.list(null);
    }

    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id){
        return teacherService.removeById(id);
    }

}

