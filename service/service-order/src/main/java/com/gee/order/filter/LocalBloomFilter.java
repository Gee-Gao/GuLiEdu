package com.gee.order.filter;

import com.gee.order.service.OrderService;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;

@Component
public class LocalBloomFilter {

    BloomFilter boomFilter;

    @Resource
    private OrderService orderService;

    public void init() {
        boomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000000);
        orderService.list(null).forEach(item -> {
            boomFilter.put(item.getOrderNo());
        });
    }

    public void put(Object object) {
        boomFilter.put(object);
    }

    public boolean check(Object object) {
        return boomFilter.mightContain(object);
    }
}
