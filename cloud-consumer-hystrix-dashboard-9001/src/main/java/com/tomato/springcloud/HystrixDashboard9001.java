package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/23 17:09
 * @className : HystrixDashboard9001
 * @description: TODO
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboard9001 {

    public static void main(String[] args) {
        //其他微服务加入依赖spring-boot-starter-actuator，启动9001访问 http://localhost:9001/hystrix
        SpringApplication.run(HystrixDashboard9001.class, args);
    }
}
