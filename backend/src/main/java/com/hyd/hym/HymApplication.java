package com.hyd.hym;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.hyd.hym.mappers")
@EnableConfigurationProperties(HymConfig.class)
public class HymApplication {

    public static void main(String[] args) {
        SpringApplication.run(HymApplication.class, args);
    }
}
