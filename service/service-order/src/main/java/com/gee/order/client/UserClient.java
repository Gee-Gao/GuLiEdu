package com.gee.order.client;

import com.gee.commonutils.vo.UcenterMemberOrderVo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Gee
 */
@Component
@FeignClient(name = "service-user", fallback = UserFileDegradeFeignClient.class)
public interface UserClient {
    //根据id返回用户信息
    @PostMapping("/eduuser/member/getUserInfoOrder/{id}")
    UcenterMemberOrderVo getUserInfoOrder(@ApiParam("用户id") @PathVariable("id") String id);
}
