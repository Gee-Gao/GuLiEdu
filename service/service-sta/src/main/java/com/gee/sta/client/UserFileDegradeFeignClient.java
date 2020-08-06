package com.gee.sta.client;

import com.gee.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author Gee
 */
@Component
public class UserFileDegradeFeignClient implements UserClient {
    @Override
    public R countRegister(String id) {
        return R.error().message("服务器维护中");
    }
}
