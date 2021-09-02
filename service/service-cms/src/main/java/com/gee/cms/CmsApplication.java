package com.gee.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Gee
 */
@SpringBootApplication
@ComponentScan("com.gee")
@MapperScan("com.gee.cms.mapper")
@EnableScheduling
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class);
    }
}
