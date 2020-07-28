package com.gee.wx.client;

import com.gee.commonutils.R;
import com.gee.wx.entity.UcenterMember;
import org.springframework.stereotype.Component;

/**
 * @author Gee
 */
@Component
//出错之后执行
public class UserFileDegradeFeignClient implements UserClient {
    @Override
    public R getOpenIdMember(UcenterMember member) {
        return R.error().message("当前服务器维护中");
    }

    @Override
    public R registerWx(UcenterMember member) {
        return R.error().message("当前服务器维护中");
    }
}
