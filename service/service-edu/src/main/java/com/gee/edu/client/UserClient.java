package com.gee.edu.client;

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
@FeignClient(name = "service-user", fallback = UserFileDegradeFeignClient.class)
public interface UserClient {
    @GetMapping("/eduuser/member/getUserInfoComment/{id}")
    R getUserInfoComment(@ApiParam("用户id") @PathVariable String id);
}
