package com.gee.order.mapper;

import com.gee.order.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
public interface PayLogMapper extends BaseMapper<PayLog> {

    // 查询付款最多的前5个用户
    List<PayLog> queryTotalMoneyPayTopFive();
}
