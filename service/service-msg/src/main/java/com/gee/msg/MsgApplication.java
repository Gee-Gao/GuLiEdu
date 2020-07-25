package com.gee.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Gee
 */
@ComponentScan({"com.gee"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MsgApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class);
    }
}
