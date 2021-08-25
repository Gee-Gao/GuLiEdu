package com.gee.order.client;

import com.gee.commonutils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Gee
 */
@Component
public class ZfbFileDegradeFeignClient implements ZfbClient {

    @Override
    public R pay(HttpServletResponse response) {
        return R.error().message("当前服务器维护中");
    }
}
