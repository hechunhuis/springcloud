package com.tomato.springcloud.service;

import com.tomato.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/7 22:27
 * @className : PaymentService
 * @description: TODO
 */
public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
