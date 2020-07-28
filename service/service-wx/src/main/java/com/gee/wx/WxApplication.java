package com.gee.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Gee
 */
@SpringBootApplication
@ComponentScan("com.gee")
@MapperScan("com.gee.wx.mapper")
@EnableFeignClients
public class WxApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class);
    }
}
