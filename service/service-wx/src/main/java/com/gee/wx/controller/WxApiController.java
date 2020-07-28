package com.gee.wx.controller;

import com.gee.commonutils.JwtUtils;
import com.gee.commonutils.R;
import com.gee.servicebase.exceptionhandler.GuliException;
import com.gee.wx.client.UserClient;
import com.gee.wx.entity.UcenterMember;
import com.gee.wx.utils.ConstantPropertiesUtil;
import com.gee.wx.utils.HttpClientUtils;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * @author Gee
 */

@Controller
@CrossOrigin
@EnableFeignClients
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Resource
    private UserClient userClient;

    //微信扫码登录
    @ApiOperation("微信扫码登录")
    @GetMapping("login")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" + "&redirect_uri=%s" + "&response_type=code" +
                "&scope=snsapi_login" + "&state=%s" + "#wechat_redirect";

        //对redirectUrl进行UrlEncoder编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("转换编码失败");
        }
        String url = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl
                , "Gee");
        return "redirect:" + url;
    }

    //微信扫码回调
    @ApiOperation("微信扫码回调")
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            //通过code,请求微信固定地址，得到token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" + "&secret=%s" + "&code=%s" + "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);

            //使用httpClient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //把accessTokenInfo转换为map集合，然后取出access_token和openid
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            //通过access_token和openid
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" + "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String userInfo = HttpClientUtils.get(userInfoUrl);

            //把用户信息转换为map对象
            HashMap<String, Object> mapUserInfo = gson.fromJson(userInfo, HashMap.class);
            //获取扫码人信息
            String headimgurl = (String) mapUserInfo.get("headimgurl");
            String nickname = (String) mapUserInfo.get("nickname");
            UcenterMember ucenterMember = new UcenterMember();
            ucenterMember.setOpenid(openid);
            ucenterMember.setNickname(nickname);
            ucenterMember.setAvatar(headimgurl);

            //调用service-user模块，查看当前用户是否注册
            R r = userClient.getOpenIdMember(ucenterMember);
            String memberJson = r.getMessage();
            //把json格式数据，转换为UcenterMember对象
            UcenterMember member = gson.fromJson(memberJson, UcenterMember.class);

            // 如果查询结果为空，进行注册
            if (member == null) {
                //设置扫码人信息
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                userClient.registerWx(member);
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //返回字符串，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "微信扫码登录失败");
        }
    }
}