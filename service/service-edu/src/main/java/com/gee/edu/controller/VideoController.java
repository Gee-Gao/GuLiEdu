package com.gee.edu.controller;


import com.gee.commonutils.R;
import com.gee.edu.entity.Video;
import com.gee.edu.service.VideoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
public class VideoController {
    @Resource
    private VideoService videoService;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video) {
System.out.println("添加执行了");
        videoService.save(video);
        return R.ok();
    }

    @ApiOperation("根据小节id查询")
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@ApiParam("小节id") @PathVariable String videoId) {
        Video video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }

    //修改小节
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video) {
System.out.println("执行了");
        videoService.updateById(video);
        return R.ok();
    }

    //删除小节
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@ApiParam("小节id") @PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }

}

