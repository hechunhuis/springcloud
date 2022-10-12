package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/12 22:33
 * @className : OrderMainZK80
 * @description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderMainZK80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainZK80.class, args);
    }
}
