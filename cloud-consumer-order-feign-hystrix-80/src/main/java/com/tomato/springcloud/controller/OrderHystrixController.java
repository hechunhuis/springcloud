package com.tomato.springcloud.controller;

import com.tomato.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 17:10
 * @className : PaymentHystrixController
 * @description: TODO
 */
@RestController
@Slf4j
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("consumer/payment/hystrix/ok/{id}")
    public String paymentReturnOK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentReturnOK(id);
        return result;
    }

    @GetMapping("consumer/payment/hystrix/timeout/{id}")
    public String paymentReturnTimeout(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentReturnTimeout(id);
        return result;
    }
}
