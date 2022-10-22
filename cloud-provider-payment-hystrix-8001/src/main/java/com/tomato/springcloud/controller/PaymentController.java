package com.tomato.springcloud.controller;

import com.tomato.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:59
 * @className : PaymentController
 * @description: TODO
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("payment/hystrix/ok/{id}")
    public String paymentReturnOK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentReturnOK(id);
        log.info("****result：" + result);
        return result;
    }

    @GetMapping("payment/hystrix/timeout/{id}")
    public String paymentReturnTimeout(@PathVariable("id") Integer id) {
        String result = paymentService.paymentReturnTimeout(id);
        log.info("****result：" + result);
        return result;
    }

    //===============服务熔断==================
    @GetMapping("payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result:" + result);
        return result;
    }
}
