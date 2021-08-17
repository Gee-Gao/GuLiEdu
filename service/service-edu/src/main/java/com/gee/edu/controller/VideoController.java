package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.client.VodClient;
import com.gee.edu.entity.Video;
import com.gee.edu.service.VideoService;
import com.gee.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
@Slf4j
public class VideoController {
    @Resource
    private VideoService videoService;
    @Resource
    private VodClient vodClient;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video) {
        log.info("小节信息" + video);
        videoService.save(video);
        return R.ok();
    }

    @ApiOperation("根据小节id查询")
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@ApiParam("小节id") @PathVariable String videoId) {
        log.info("小节id" + videoId);
        Video video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }

    //修改小节
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video) {
        log.info("小节信息" + video);
        videoService.updateById(video);
        return R.ok();
    }

    //删除小节
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@ApiParam("小节id") @PathVariable String id) {
        log.info("小节id" + id);
        //根据小节id获取视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();

        //删除阿里云视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAiliVideo(videoSourceId);
            if (result.getCode() == 20001) {
                log.error("阿里云删除视频失败");
                throw new GuliException(20001, "删除视频失败");
            }
        }

        //删除小节
        videoService.removeById(id);
        return R.ok();
    }
}

