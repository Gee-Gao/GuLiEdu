package com.gee.oss.controller;

import com.gee.commonutils.R;
import com.gee.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("eduoss/fileoss")
public class OssController {
    @Resource
    private OssService ossService;

    @ApiOperation("头像上传")
    @PostMapping
    public R uploadOssFile(@ApiParam("上传头像文件") MultipartFile file) {
        //获取上传文件
        //获得上传到OSS的路劲
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }

}
