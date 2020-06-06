package com.gee.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.edu.entity.Teacher;
import com.gee.edu.entity.vo.TeacherQuery;
import com.gee.edu.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
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

    //查询所有讲师数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        //创建Page对象
        Page<Teacher> pageParam = new Page<>(page, limit);

        //调用方式实现分页
        teacherService.page(pageParam, null);
        //数据list集合
        List<Teacher> records = pageParam.getRecords();
        //数据总记录数
        long total = pageParam.getTotal();
        //返回结果
        return R.ok().data("total", total).data("rows", records);
    }

    //条件查询
    @ApiOperation(value = "条件查询分页讲师列表")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true)
                                  @PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)
                                  @PathVariable Long limit,
                                  @ApiParam(name = "teacherQuery", value = "查询对象")
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建分页对象
        Page<Teacher> pageTeacher = new Page<>(page, limit);
        //构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        //多条件组合查询,判断值是否为空，不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);

        //总记录数
        long total = pageTeacher.getTotal();
        //数据集合
        List<Teacher> records = pageTeacher.getRecords();
        //返回数据
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(name = "teacher", value = "添加对象") @RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        //返回添加结果
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

