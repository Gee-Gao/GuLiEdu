package com.gee.edu.service;

import com.gee.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);
}
