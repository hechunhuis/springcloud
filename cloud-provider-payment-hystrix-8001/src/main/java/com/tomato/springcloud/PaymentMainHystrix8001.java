package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:46
 * @className : PaymentMainHystrix8001
 * @description: 断路器
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //Hystrix添加注解，开启服务降级配置
public class PaymentMainHystrix8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMainHystrix8001.class, args);
    }
}
