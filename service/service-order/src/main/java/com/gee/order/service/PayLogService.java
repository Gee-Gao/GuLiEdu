package com.gee.order.service;

import com.gee.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
public interface PayLogService extends IService<PayLog> {
    Map createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
