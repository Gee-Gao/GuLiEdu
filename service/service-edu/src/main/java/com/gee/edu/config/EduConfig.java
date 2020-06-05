package com.gee.edu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.gee.edu.mapper")
public class EduConfig {

}
