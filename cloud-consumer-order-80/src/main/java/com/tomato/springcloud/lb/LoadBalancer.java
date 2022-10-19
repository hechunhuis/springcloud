package com.tomato.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/19 21:22
 * @className : LoadBalancer
 * @description: 手写负载均衡
 */
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
