package com.gee.order.service.impl;

import com.gee.order.entity.Order;
import com.gee.order.mapper.OrderMapper;
import com.gee.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
