package com.gee.order.init;

import com.gee.order.filter.LocalBloomFilter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BloomFilterInit implements ApplicationRunner {
    @Resource
    private LocalBloomFilter localBloomFilter;

    @Override
    public void run(ApplicationArguments args) {
        localBloomFilter.init();
    }
}
