package com.gee.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.CourseDescription;
import com.gee.edu.entity.vo.CourseInfoVo;
import com.gee.edu.entity.vo.CoursePublishVo;
import com.gee.edu.mapper.CourseMapper;
import com.gee.edu.service.CourseDescriptionService;
import com.gee.edu.service.CourseService;
import com.gee.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private CourseDescriptionService courseDescriptionService;

    //添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程基本信息
        //CourseInfoVo对象转换为Course对象
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);
        //添加失败
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程失败");
        }

        //获取添加之后的课程id
        String cid = course.getId();

        //向课程描述表添加信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getCoursePublishInfo(id);
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //查询课程表
        Course course = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(course, courseInfoVo);

        //查询描述表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription, courseInfoVo);
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update <= 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //修改描述表
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        courseDescriptionService.updateById(courseDescription);

    }
}
