package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.annotation.WebListener;

@EnableScheduling//启动定时器。发现注解
@SpringBootApplication
@MapperScan("com.fh.shop.api.*.mapper")
public class ShopApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiSpringbootApplication.class, args);
    }

}
