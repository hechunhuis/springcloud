package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/20 22:16
 * @className : OrderMainFeign80
 * @description: OpenFeign 用于客户端
 */
@SpringBootApplication
@EnableFeignClients
public class OrderMainFeign80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeign80.class, args);
    }
}
