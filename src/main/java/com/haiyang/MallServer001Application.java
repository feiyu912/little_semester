package com.haiyang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.haiyang.mapper")
public class MallServer001Application {
    public static void main(String[] args) {
        SpringApplication.run(MallServer001Application.class, args);
    }
}
