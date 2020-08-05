package com.gee.order.service;

import com.gee.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);
}
