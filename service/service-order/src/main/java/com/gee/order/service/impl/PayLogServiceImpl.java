package com.gee.order.service.impl;

import com.gee.order.entity.PayLog;
import com.gee.order.mapper.PayLogMapper;
import com.gee.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
