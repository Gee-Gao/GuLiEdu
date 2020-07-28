package com.gee.wx.client;

import com.gee.commonutils.R;
import com.gee.wx.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Gee
 */
@FeignClient(name = "service-user", fallback = UserFileDegradeFeignClient.class)
public interface UserClient {
    //根据openid获取数据库中是否注册
    @PostMapping("eduuser/member/getOpenIdMember")
    R getOpenIdMember(UcenterMember member);

    //微信注册
    @PostMapping("eduuser/member/registerWx")
    R registerWx(UcenterMember member);
}
