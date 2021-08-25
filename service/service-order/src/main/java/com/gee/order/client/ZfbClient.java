package com.gee.order.client;

import com.gee.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@FeignClient(name = "service-zfb", fallback = ZfbFileDegradeFeignClient.class)
public interface ZfbClient {
    @GetMapping("alipay")
    R pay(HttpServletResponse response);
}
