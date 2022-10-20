package com.tomato.springcloud.controller;

import com.tomato.springcloud.commons.CommonResult;
import com.tomato.springcloud.entities.Payment;
import com.tomato.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/20 22:23
 * @className : OrderFeignController
 * @description: TODO
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String consumerPaymentFeignTimeout() {
        //Openfeign 底层Ribbon 默认1秒报错，服务方设置3秒等待
        return paymentFeignService.paymentFeignTimeout();
    }
}
