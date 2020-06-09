package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
    public R addSubject(MultipartFile file){
        //上传过来的文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
}

