package com.gee.sta.client;

import com.gee.commonutils.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Gee
 */
@Component
@FeignClient(value = "service-user", fallback = UserFileDegradeFeignClient.class)
public interface UserClient {
    @GetMapping("/eduuser/member/countRegister/{day}")
    R countRegister(@ApiParam("日期") @PathVariable("day") String day);
}
