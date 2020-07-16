package com.gee.edu.service;

import com.gee.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gee.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
public interface ChapterService extends IService<Chapter> {
    //课程大纲列表,根据课程id进行查询
    List<ChapterVo> etChapterVideoByCourseId(String courseId);

    //删除章节
    boolean deleteChapter(String chapterId);

    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
