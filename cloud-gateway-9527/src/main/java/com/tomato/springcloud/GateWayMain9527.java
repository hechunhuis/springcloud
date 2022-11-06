package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/6 13:12
 * @className : GateWayMain9527
 * @description: TODO
 */
@SpringBootApplication
@EnableEurekaClient
public class GateWayMain9527 {
    //测试开启eureka7001、PaymentMain8001、PaymentMain8002、GateWayMain9527
    public static void main(String[] args) {
        SpringApplication.run(GateWayMain9527.class, args);
    }
}
