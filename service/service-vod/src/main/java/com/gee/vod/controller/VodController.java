package com.gee.vod.controller;

import com.gee.commonutils.R;
import com.gee.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
