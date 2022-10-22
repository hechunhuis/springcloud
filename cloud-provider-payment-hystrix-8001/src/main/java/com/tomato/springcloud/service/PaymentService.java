package com.tomato.springcloud.service;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:52
 * @className : PaymentService
 * @description: TODO
 */
public interface PaymentService {

    String paymentReturnOK(Integer id);

    String paymentReturnTimeout(Integer id);
}
