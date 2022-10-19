package com.tomato.springcloud.controller;

import com.tomato.springcloud.commons.CommonResult;
import com.tomato.springcloud.entities.Payment;
import com.tomato.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/8 20:56
 * @className : OrderController
 * @description: TODO
 */
@RestController
@Slf4j
public class OrderController {

    private static final String PAYMENT_NAME = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource //自定义手写负载均衡
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * postForObject
     * @param payment
     * @return
     */
    @PostMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("调用：/consumer/payment/create");
        return restTemplate.postForObject(PAYMENT_NAME + "/payment/create", payment, CommonResult.class);
    }

    /**
     * getForObject
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        log.info("调用：/consumer/payment/get/{id}");
        return restTemplate.getForObject(PAYMENT_NAME + "/payment/get/" + id, CommonResult.class);
    }

    /**
     * postForEntity
     * @param payment
     * @return
     */
    @PostMapping("/consumer/payment/createForEntity")
    public CommonResult<Payment> createForEntity(Payment payment) {
        log.info("调用：/consumer/payment/create");
        ResponseEntity<CommonResult> responseEntity = restTemplate.postForEntity(PAYMENT_NAME + "/payment/create", payment, CommonResult.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return new CommonResult(444, "操作失败");
        }
    }

    /**
     * getForEntity
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPaymentForEntity(@PathVariable("id") Long id) {
        log.info("调用：/consumer/payment/getForEntity/{id}");
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_NAME + "/payment/get/" + id, CommonResult.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return new CommonResult(444, "操作失败");
        }
    }

    /**
     * 手写负载均衡
     * @return
     */
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class, String.class);
    }
}
