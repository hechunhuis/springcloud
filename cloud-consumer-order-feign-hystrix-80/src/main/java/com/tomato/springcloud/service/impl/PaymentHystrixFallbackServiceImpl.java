package com.tomato.springcloud.service.impl;

import com.tomato.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 18:31
 * @className : PaymentHystrixFallback
 * @description: TODO
 */
@Component
public class PaymentHystrixFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentReturnOK(Integer id) {
        return "PaymentHystrixFallbackServiceImpl paymentReturnOK o(╥﹏╥)o";
    }

    @Override
    public String paymentReturnTimeout(Integer id) {
        return "PaymentHystrixFallbackServiceImpl paymentReturnTimeout o(╥﹏╥)o";
    }
}
