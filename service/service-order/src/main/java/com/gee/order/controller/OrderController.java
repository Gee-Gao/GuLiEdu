package com.gee.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gee.commonutils.JwtUtils;
import com.gee.commonutils.R;
import com.gee.order.entity.Order;
import com.gee.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Resource
    private OrderService orderService;

    //创建订单
    @ApiOperation("创建订单")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@ApiParam("课程id") @PathVariable String courseId, @ApiParam("请求体对象") HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单，返回订单号
        String orderNo = orderService.createOrder(courseId, memberId);
        return R.ok().data("orderNo", orderNo);
    }

    //根据订单id查询订单信息
    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@ApiParam("订单id") @PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    //根据课程id和用户id查询订单表的支付状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@ApiParam("课程id") @PathVariable("courseId") String courseId,
                               @ApiParam("用户id") @PathVariable("memberId") String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", "1");
        int count = orderService.count(wrapper);
        return count > 0;
    }
}

