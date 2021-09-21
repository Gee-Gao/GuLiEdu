package com.gee.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gee.commonutils.R;
import com.gee.commonutils.vo.CourseOrderVo;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.vo.CourseInfoVo;
import com.gee.edu.entity.vo.CoursePublishVo;
import com.gee.edu.entity.vo.CourseQuery;
import com.gee.edu.entity.vo.CourseWebVo;
import com.gee.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
@Slf4j
public class CourseController {
    @Resource
    private CourseService courseService;

    //根据id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@ApiParam("课程id") @PathVariable String id) {
        log.info("课程查询id:{}", id);
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        //把baseCourseInfo对象中的值赋值到courseOrderVo
        BeanUtils.copyProperties(baseCourseInfo, courseOrderVo);
        log.info("课程查询结果:{}", courseOrderVo);
        return courseOrderVo;
    }

    //条件查询
    @ApiOperation(value = "条件查询分页课程列表")
    @PostMapping("pageCourseCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true)
                                  @PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)
                                  @PathVariable Long limit,
                                  @ApiParam(name = "courseQuery", value = "查询对象")
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        //创建分页对象
        Page<Course> pageCourse = new Page<>(page, limit);
        //构建条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //多条件组合查询,判断值是否为空，不为空拼接条件
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        wrapper.like(!StringUtils.isEmpty(title), "title", title);
        wrapper.eq(!StringUtils.isEmpty(status), "status", status);

        //调用方法实现条件查询分页
        courseService.page(pageCourse, wrapper);

        //总记录数
        long total = pageCourse.getTotal();
        //数据集合
        List<Course> records = pageCourse.getRecords();
        log.info("课程查询结果:{}", records);
        //返回数据
        return R.ok().data("total", total).data("rows", records);
    }

    //分页查询
    @ApiOperation("分页查询")
    @GetMapping("pageCourse/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        //创建Page对象
        Page<Course> pageParam = new Page<>(page, limit);

        //调用方式实现分页
        courseService.page(pageParam, null);
        //数据list集合
        List<Course> records = pageParam.getRecords();
        //数据总记录数
        long total = pageParam.getTotal();
        log.info("课程查询结果:{}", records);
        //返回结果
        return R.ok().data("total", total).data("rows", records);
    }

    //添加课程基本信息
    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam("课程信息对象") @RequestBody CourseInfoVo courseInfoVo) {
        log.info("课程信息:{}", courseInfoVo);
        //返回添加课程的id,方便添加课程大纲
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据课程id查询课程基本信息
    @ApiOperation("根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@ApiParam("课程id") @PathVariable String courseId) {
        log.info("课程id:{}", courseId);
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@ApiParam("课程信息对象") @RequestBody CourseInfoVo courseInfoVo) {
        log.info("课程信息:{}", courseInfoVo);
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@ApiParam("课程id") @PathVariable String id) {
        log.info("课程id:{}", id);
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布，修改课程状态
    @ApiOperation("课程最终发布，修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@ApiParam("课程id") @PathVariable("id") String id) {
        log.info("课程id:{}", id);
        Course course = new Course();
        course.setId(id);
        course.setStatus("1");
        courseService.updateById(course);
        return R.ok();
    }

    //删除课程
    @ApiOperation("删除课程")
    @DeleteMapping("{courseId}")
    public R deleteCourse(@ApiParam("课程id") @PathVariable String courseId) {
        log.info("课程id:{}", courseId);
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

