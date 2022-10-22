package com.tomato.springcloud.service;

import com.tomato.springcloud.service.impl.PaymentHystrixFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 17:08
 * @className : PaymentHystrixService
 * @description: TODO
 */
@Component
@FeignClient(value = "CLOUD-PROVIDER-PAYMENT-HYSTRIX", fallback = PaymentHystrixFallbackServiceImpl.class)
public interface PaymentHystrixService {

    @GetMapping("payment/hystrix/ok/{id}")
    String paymentReturnOK(@PathVariable("id") Integer id);

    @GetMapping("payment/hystrix/timeout/{id}")
    String paymentReturnTimeout(@PathVariable("id") Integer id);
}
