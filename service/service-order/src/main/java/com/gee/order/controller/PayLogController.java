package com.gee.order.controller;


import com.gee.commonutils.R;
import com.gee.order.client.ZfbClient;
import com.gee.order.entity.PayLog;
import com.gee.order.filter.LocalBloomFilter;
import com.gee.order.service.PayLogService;
import com.gee.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Gee
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {
    @Resource
    private LocalBloomFilter localBloomFilter;
    @Resource
    private PayLogService payLogService;
    @Resource
    private ZfbClient zfbClient;

    // 查询付款最多的前5个用户
    @ApiOperation("查询付款最多的前5个用户")
    @GetMapping("queryTotalMoneyPayTopFive")
    public R queryTotalMoneyPayTopFive() {
        List<PayLog> payLogs = payLogService.queryTotalMoneyPayTopFive();
        return R.ok().data("payTopFive", payLogs);
    }


    //生成微信支付二维码
    @ApiOperation("生成微信支付二维码")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@ApiParam @PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    //查询订单支付状态
    @ApiOperation("查询订单支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@ApiParam("订单号") @PathVariable String orderNo) {
        if (!localBloomFilter.check(orderNo)) {
            throw new GuliException(20001, "数据库不存在数据");
        }

        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付失败");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

    @ApiOperation("生成支付宝支付二维码")
    @GetMapping("zfb/pay")
    public R zfbPay(HttpServletResponse response) {
        return zfbClient.pay(response);
    }
}

