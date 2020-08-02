package com.gee.edu.client;

import com.gee.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author Gee
 */
@Component
public class UserFileDegradeFeignClient implements UserClient{
    @Override
    public R getUserInfoComment(String id) {
        return R.error().message("服务器维护中");
    }
}
