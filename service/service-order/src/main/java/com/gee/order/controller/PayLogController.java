package com.gee.order.controller;


import com.gee.commonutils.R;
import com.gee.order.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private PayLogService payLogService;

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

}

