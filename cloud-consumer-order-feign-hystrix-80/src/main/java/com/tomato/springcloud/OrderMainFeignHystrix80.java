package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 17:04
 * @className : OrderMainFeignHystrix80
 * @description: TODO
 */
@SpringBootApplication
@EnableFeignClients
public class OrderMainFeignHystrix80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeignHystrix80.class, args);
    }
}
