package com.tomato.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
    @HystrixCommand(fallbackMethod = "paymentReturnTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentReturnTimeout(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentReturnTimeout(id);
        return result;
    }

    public String paymentReturnTimeoutHandler(@PathVariable("id") Integer id) {
        return "我是消费者，支付服务繁忙或自检出错，请检查o(╥﹏╥)o";
    }
}
