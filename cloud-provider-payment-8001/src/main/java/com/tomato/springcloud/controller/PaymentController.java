package com.tomato.springcloud.controller;

import com.tomato.springcloud.commons.CommonResult;
import com.tomato.springcloud.entities.Payment;
import com.tomato.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/7 22:32
 * @className : PaymentController
 * @description: 支付服务路由
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*********插入结果：" + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功，执行服务端口:" + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败！执行服务端口:" + serverPort, result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*********查询结果：" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功,执行服务端口:" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有查询到记录！,执行服务端口:" + serverPort, null);
        }
    }
}
