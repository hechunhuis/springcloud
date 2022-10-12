package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/12 23:02
 * @className : OrderMainConsul80
 * @description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderMainConsul80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainConsul80.class, args);
    }
}
