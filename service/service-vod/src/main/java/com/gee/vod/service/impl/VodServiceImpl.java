package com.gee.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.vod.service.VodService;
import com.gee.vod.utils.ConstantPropertiesUtil;
import com.gee.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Gee
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {
    //根据视频id集合删除多个阿里云视频
    @Override
    public void removeMoreAliVideo(List<String> videoIdList) {
        log.info("待删除视频列表" + videoIdList);
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置要删除视频的id,若有多个，逗号分隔
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(videoIds);
            //调用初始化对象的方法，实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("阿里云视频删除失败");
            throw new GuliException(20001, "删除云端视频失败");
        }
    }

    //根据视频id删除阿里云视频
    @Override
    public void removeVideo(String id) {
        log.info("待删除视频id" + id);
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置要删除视频的id,若有多个，逗号分隔
            request.setVideoIds(id);
            //调用初始化对象的方法，实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("阿里云视频删除失败");
            throw new GuliException(20001, "删除云端视频失败");
        }
    }

    //视频上传到阿里云
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            log.info("开始上传视频");
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            log.info("视频上传成功视频id" + videoId);
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    log.info("视频上传失败");
                    throw new GuliException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }
}
