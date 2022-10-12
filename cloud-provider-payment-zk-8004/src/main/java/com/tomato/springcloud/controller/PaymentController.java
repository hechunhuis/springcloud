package com.tomato.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/10 22:37
 * @className : PaymentController
 * @description: TODO
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/zk")
    public String paymentzk() {
        return "SpringCloud with Zookeeper: " + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
