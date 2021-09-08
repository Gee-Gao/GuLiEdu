package com.gee.order.mapper;

import com.gee.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> queryCreateOrderTopFive();
}
