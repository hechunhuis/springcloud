package com.tomato.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tomato.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:52
 * @className : PaymentServiceImpl
 * @description: TODO
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    //==============================服务降级===========================================
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

    //==============================服务熔断===========================================

    @Override
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到60%多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("*******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }
    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，o(╥﹏╥)o id:" + id;
    }
}
