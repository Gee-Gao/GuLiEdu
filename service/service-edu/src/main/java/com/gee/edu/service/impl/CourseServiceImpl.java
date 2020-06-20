package com.gee.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.CourseDescription;
import com.gee.edu.entity.vo.CourseInfoVo;
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
}
