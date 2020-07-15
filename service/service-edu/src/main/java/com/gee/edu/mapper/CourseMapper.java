package com.gee.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gee.edu.entity.Course;
import com.gee.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublishInfo(String courseID);

}
