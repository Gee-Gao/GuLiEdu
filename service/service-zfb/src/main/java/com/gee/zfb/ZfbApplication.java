package com.gee.zfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.gee")
@SpringBootApplication
public class ZfbApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZfbApplication.class);
    }
}
