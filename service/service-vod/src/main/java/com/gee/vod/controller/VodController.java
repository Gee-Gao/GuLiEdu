package com.gee.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.gee.commonutils.R;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.vod.service.VodService;
import com.gee.vod.utils.ConstantPropertiesUtil;
import com.gee.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gee
 */
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    private static Map<String, String> playAuthMap = new HashMap<>();

    @Resource
    private VodService vodService;

    //视频上传到阿里云
    @ApiOperation("视频上传到阿里云")
    @PostMapping("uploadAliVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id删除阿里云视频
    @ApiOperation("根据视频id删除阿里云视频")
    @DeleteMapping("removeAiliVideo/{id}")
    public R removeAiliVideo(@ApiParam("视频id") @PathVariable String id) {
        vodService.removeVideo(id);
        return R.ok();
    }

    //根据视频id集合删除多个阿里云视频
    @ApiOperation("根据视频id集合删除多个阿里云视频")
    @DeleteMapping("delete-batch")
    public R deleteBatch(@ApiParam("需要删除的视频id集合") @RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAliVideo(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@ApiParam("视频id") @PathVariable String id) {
        // 判断缓存中是否存在视频凭证
        String playAuth = playAuthMap.get(id);
        if (playAuth != null) {
            return R.ok().data("playAuth", playAuth);
        }

        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建获取凭证request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            playAuth = response.getPlayAuth();
            playAuthMap.put(id, playAuth);
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取视频凭证失败");
        }
    }
}
