package com.gee.edu.service;

import com.gee.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gee.edu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
public interface CourseService extends IService<Course> {

   String saveCourseInfo(CourseInfoVo courseInfoVo);
}
