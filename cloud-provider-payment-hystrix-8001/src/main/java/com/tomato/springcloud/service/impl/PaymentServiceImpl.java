package com.tomato.springcloud.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tomato.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:52
 * @className : PaymentServiceImpl
 * @description: TODO
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentReturnOK(Integer id) {
        return String.format("线程池：%s\npaymentReturnOK\nid：%s",Thread.currentThread().getName(),id);
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentReturnTimeoutHandler",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentReturnTimeout(Integer id) {
        //暂停timeOut秒钟
        int timeOut = 3;
        try {
            TimeUnit.SECONDS.sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("线程池：%s\npaymentReturnTimeout\nid：%s\n耗时：%s秒钟 O(∩_∩)O哈哈~",Thread.currentThread().getName(),id, timeOut);
    }

    public String paymentReturnTimeoutHandler(Integer id) {
        return String.format("线程池：%s\npaymentReturnTimeoutHandler\nid：%s\n o(╥﹏╥)o",Thread.currentThread().getName(),id);

    }
}
