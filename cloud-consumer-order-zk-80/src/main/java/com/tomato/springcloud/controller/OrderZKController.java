package com.tomato.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/12 22:36
 * @className : OrderZKController
 * @description: TODO
 */
@RestController
@Slf4j
public class OrderZKController {
    public static final String INVOKE_URL = "http://clou-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/zk")
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
    }
}
