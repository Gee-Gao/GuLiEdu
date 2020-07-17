package com.gee.vod.controller;

import com.gee.commonutils.R;
import com.gee.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Gee
 */
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Resource
    private VodService vodService;

    //视频上传到阿里云
    @ApiOperation("视频上传到阿里云")
    @PostMapping("uploadAliVideo")
    public R uploadVideo(MultipartFile file){
        String videoId =  vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("removeAiliVideo/{id}")
    public R removeAiliVideo(@ApiParam("视频id") @PathVariable String id){
        vodService.removeVideo(id);
        return R.ok();
    }
}
