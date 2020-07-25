package com.gee.msg.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.gee.msg.service.MsgService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Gee
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G5Zf81jhYfrfZpzpfx2", "ukjsTIBpxy2WYlNxVtAhRct2SmJcNV");
        IAcsClient client = new DefaultAcsClient(profile);
        //设置相关参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        //设置发送参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "GQ商城");
        request.putQueryParameter("TemplateCode", "SMS_189622031");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
