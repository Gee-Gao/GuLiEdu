package com.gee.vodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author Gee
 */
public class TestVod {
    public static void main(String[] args) throws ClientException {
        //根据视频id获取播放凭证
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G5Zf81jhYfrfZpzpfx2", "ukjsTIBpxy2WYlNxVtAhRct2SmJcNV");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response;
        request.setVideoId("5844a5a2fcba4b389c6b66e38269d7be");
        //调用初始化对象的方法，传递request，获取数据
        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());
    }


    //根据视频id获取播放地址
    public static void getPlayUrl() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G5Zf81jhYfrfZpzpfx2", "ukjsTIBpxy2WYlNxVtAhRct2SmJcNV");
        //创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;
        //向request对象设置视频id
        request.setVideoId("5844a5a2fcba4b389c6b66e38269d7be");
        //调用初始化对象的方法，传递request，获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
