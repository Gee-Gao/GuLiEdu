package com.gee.edu.client;

import com.gee.commonutils.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Gee
 */
@Component
@FeignClient("service-vod")
public interface VodClient {
    //定义调用方法的路径
    //根据视频id删除阿里云视频
    @DeleteMapping("eduvod/video/removeAiliVideo/{id}")
    R removeAiliVideo(@ApiParam("视频id") @PathVariable("id") String id);
}
