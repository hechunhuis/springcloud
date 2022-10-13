# 一. 微服务架构概述

## 1. 什么是微服务

微服务架构是一种架构模式，他提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务于服务间采用轻量级的通信机制互相协助（通常是基于HTTP协议的Restful API）。每个服务都围绕这具体业务进行构建，并且能够被独立的部署到生产环境、类生产环境等。另外，应当尽量避免统一的、集中式的服务管理机制，对具体的一个服务而言，应根据业务上下文，选择合适的语言、工具对其进行构建。

## 2. Spring Cloud简介





## 3. SpringCloud技术栈

1. 服务注册与发现【eureka】
2. 服务负载与调用【Netflix oss ribbon】
3. 服务负载与调用【Netflix feign】
4. 服务熔断降级【hystrix】
5. 服务网关【zuul】
6. 服务分布式与配置【spring cloud config】
7. 服务开发【springboot】

# 二. 技术选型

1. cloud：Hoxton.SR1
2. boot：2.2.2.RELEASE
3. cloud alibaba：2.1.0.RELEASE
4. Java：Java8
5. Maven：3.5及以上
6. Mysql：5.7及以上

# 三. Eureka服务注册中心

## 1. 什么是服务治理

Springcloud封装了Netflix公司开发的Eureka模块来实现服务治理

在传统的RPC远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，所以需要使用服务治理，管理服务与服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册。

## 2. 什么是服务注册与发现

Eureka采用了CS的设计架构，Eureka Server作为服务注册功能的服务器，它是服务注册中心。二系统中的其他微服务，使用Eureka的客户端连接到Eureka Server并维持心跳连接。这样系统的维护人员就可以通过Eureka Server来监控系统中的各个微服务是否正常运行。

在服务注册与发现中，有一个注册中心，当服务器启动的时候，会把当前自己服务器的信息，比如服务地址、通讯地址等以别名方式注册到注册中心上，另一方（消费者|服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用，RPC远程调用框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系（服务治理概念）。在任何RPC远程框架中，都会有一个注册中心（存放服务地址相关信息【接口地址】）

## 3. Eureka常用的两个组件

- **Eureka Server** 提供服务注册

  各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。

- **Eureka Client** 通过注册中心进行访问

  是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳（默认周期为30秒）。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中将这个服务节点移除（默认90秒）。

# 四. Ribbon负载均衡
## 1. 负载均衡算法策略
1.com.netflix.loadbalancer.RoundRobinRule 轮询
2.com.netflix.loadbalancer.RandomRule     随机
3.com.netflix.loadbalancer.RetryRule      先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重置，获取可用服务
4.WeightedResponseTimeRule                对RoundRobinRule的扩展，响应速度越快的示例选择权重越大，越容易被选择
5.BestAvailableRule                       会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
6.AvailabilityFilteringRule               先过滤掉故障实例，再选择并发较小的实例
7.ZoneAvoidanceRule                       默认规则，复合判断server所在区域的性能和server的可用性选择服务器
## 2. 替换负载规则
注意：自定义配置不能放到ComponentScan扫描的包以及子包下
1.创建配置类
```java
package com.tomato.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Rule
 * @Description ribbon的负载均衡规则替换
 * @Author hch
 * @Email hechunhui_email@163.com
 * @Date 2022/10/13 19:21
 * @Version 1.0
 **/
@Configuration
public class Rule {

  @Bean
  public IRule rule() {
//      return new RetryRule();
//      return new WeightedResponseTimeRule();
//      return new BestAvailableRule();
//      return new AvailabilityFilteringRule();
//      return new ZoneAvoidanceRule();
    return new RandomRule(); //定义为随机

  }
}
```
2.在启动类上添加@RibbonClient注解
```java
package com.tomato.springcloud;

import com.tomato.ribbon.Rule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/8 20:54
 * @className : OrderMain
 * @description: 订单模块主启动类
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD_PAYMENT_SERVICE", configuration = Rule.class)
public class OrderMain80 {
  public static void main(String[] args) {
    SpringApplication.run(OrderMain80.class, args);
  }
}
```


