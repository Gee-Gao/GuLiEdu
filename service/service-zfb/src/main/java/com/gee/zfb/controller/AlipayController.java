package com.gee.zfb.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gee.commonutils.R;
import com.gee.zfb.config.AlipayConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AlipayController {
    @GetMapping("alipay")
    public R pay(HttpServletResponse response) throws AlipayApiException, IOException {
        DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, AlipayConfig.format,
                AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        alipayTradePagePayRequest.setReturnUrl("http:gwj.gaoyunqiang.top");
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", System.currentTimeMillis());
        map.put("product_code", "FAST_INSTANT_TRADE_PAY");
        map.put("total_amount", "20");
        map.put("subject", "Gwj");
        String jsonString = JSONObject.toJSONString(map);
        alipayTradePagePayRequest.setBizContent(jsonString);
        String body = defaultAlipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(body);
        response.getWriter().flush();
        response.getWriter().close();

        return R.ok();
    }
}
