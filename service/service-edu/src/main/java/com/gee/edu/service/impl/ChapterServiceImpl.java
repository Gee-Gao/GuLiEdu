package com.gee.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gee.edu.entity.Chapter;
import com.gee.edu.entity.Video;
import com.gee.edu.entity.chapter.ChapterVo;
import com.gee.edu.entity.chapter.VideoVo;
import com.gee.edu.mapper.ChapterMapper;
import com.gee.edu.service.ChapterService;
import com.gee.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Resource
    private VideoService videoService;

    //课程大纲列表,根据课程id进行查询
    @Override
    public List<ChapterVo> etChapterVideoByCourseId(String courseId) {
        //根据id查询课程所有章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<Chapter> chapters = baseMapper.selectList(wrapperChapter);

        //根据id查询课程所有小节
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<Video> videos = videoService.list(wrapperVideo);

        //创建集合，用于封装最终数据
        List<ChapterVo> finallList = new ArrayList<>();

        //遍历查询章节list进行封装
        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finallList.add(chapterVo);

            //创建集合，用于封装小节
            List<VideoVo> videoList = new ArrayList<>();

            //遍历查询小姐list进行封装
            for (Video video : videos) {
                //判断小节里面的id和章节的id是否相同
                if(video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoList.add(videoVo);
                }
            }
            //把封装之后的小节list,放到章节对象中
            chapterVo.setChildren(videoList);
        }

        return finallList;
    }
}
