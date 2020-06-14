package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.entity.subject.OneSubject;
import com.gee.edu.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-06-09
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取过来
    @ApiOperation("添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来的文件
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @ApiOperation("课程分类列表（树形）")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

