package com.gee.edu.client;

import com.gee.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Gee
 */
@Component
//出错之后执行
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAiliVideo(String id) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频失败");
    }
}
