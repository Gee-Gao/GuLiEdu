package com.gee.order.init;

import com.gee.order.filter.LocalBloomFilter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BloomFilterInit implements ApplicationRunner {
    @Resource
    private LocalBloomFilter localBloomFilter;

    @Override
    // 每两小时重置一次布隆过滤器，降低误判率
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 2L)
    public void run(ApplicationArguments args) {
        localBloomFilter.init();
    }
}
