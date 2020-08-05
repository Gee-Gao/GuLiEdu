package com.gee.edu.client;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Gee
 */
@Component
@FeignClient(value = "service-order",fallback = OrderFileDegradeFeignClient.class)
public interface OrderClient {

    //根据课程id和用户id查询订单表的支付状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@ApiParam("课程id") @PathVariable("courseId") String courseId,
                               @ApiParam("用户id") @PathVariable("memberId") String memberId);
}
