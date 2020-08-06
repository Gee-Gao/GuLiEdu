package com.gee.sta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Gee
 */
@SpringBootApplication
@ComponentScan("com.gee")
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.gee.sta.mapper")
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class);
    }
}
