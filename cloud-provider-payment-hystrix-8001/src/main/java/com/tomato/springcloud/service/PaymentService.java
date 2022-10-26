package com.tomato.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:52
 * @className : PaymentService
 * @description: TODO
 */
public interface PaymentService {

    //=========服务降级================
    String paymentReturnOK(Integer id);

    String paymentReturnTimeout(Integer id);

    //========服务熔断=================
    String paymentCircuitBreaker(Integer id);
}
