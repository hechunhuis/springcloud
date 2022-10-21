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

# 二. 技术学习路线

服务注册中心：[Eureka](#三. Eureka服务注册中心) -> **[Zookeeper](#四. Zookeeper) -> [Consul](#五. Consul) -> Nacos**

服务调用：**[Ribbon](#六. Ribbon负载均衡) -> LoadBalancer**

服务调用2：Feign -> **[OpenFeign](#七. OpenFeign)**

服务降级：Hystrix -> **resilience4j -> sentinel**

服务网关：Zuul -> Zuul2 -> **gateway**

服务配置：Config -> **Nacos**

服务总线：Bus -> **Nacos**

# 三. Eureka服务注册中心（已停更）

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

# 四. Zookeeper

ZooKeeper是一个[分布式](https://baike.baidu.com/item/%E5%88%86%E5%B8%83%E5%BC%8F/19276232?fromModule=lemma_inlink)的，开放源码的[分布式应用程序](https://baike.baidu.com/item/%E5%88%86%E5%B8%83%E5%BC%8F%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F/9854429?fromModule=lemma_inlink)协调服务，是[Google](https://baike.baidu.com/item/Google?fromModule=lemma_inlink)的Chubby一个[开源](https://baike.baidu.com/item/%E5%BC%80%E6%BA%90/246339?fromModule=lemma_inlink)的实现，是Hadoop和[Hbase](https://baike.baidu.com/item/Hbase/7670213?fromModule=lemma_inlink)的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。

ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。

ZooKeeper包含一个简单的原语集，提供Java和C的接口。

ZooKeeper代码版本中，提供了分布式独享锁、选举、队列的接口，代码在$zookeeper_home\src\recipes。其中分布锁和队列有[Java](https://baike.baidu.com/item/Java/85979?fromModule=lemma_inlink)和C两个版本，选举只有Java版本。

# 五. Consul

Consul是HashiCorp公司推出的开源工具，用于实现分布式系统的服务发现与配置。 Consul是分布式的、高可用的、可横向扩展的。它具备以下特性 :

服务发现：consul通过DNS或者HTTP接口使服务注册和服务发现变的很容易，一些外部服务，例如saas提供的也可以一样注册。
健康检查：健康检测使consul可以快速的告警在集群中的操作。和服务发现的集成，可以防止服务转发到故障的服务上面。
键/值存储：一个用来存储动态配置的系统。提供简单的HTTP接口，可以在任何地方操作。
多数据中心：无需复杂的配置，即可支持任意数量的区域。

## 1. 优势

使用 Raft 算法来保证一致性, 比复杂的 Paxos 算法更直接. 相比较而言, zookeeper 采用的是 Paxos, 而 etcd 使用的则是 Raft. 
支持多数据中心，内外网的服务采用不同的端口进行监听。 多数据中心集群可以避免单数据中心的单点故障,而其部署则需要考虑网络延迟, 分片等情况等. zookeeper 和 etcd 均不提供多数据中心功能的支持. 
支持健康检查. etcd 不提供此功能. 
支持 http 和 dns 协议接口. zookeeper 的集成较为复杂, etcd 只支持 http 协议. 
官方提供web管理界面, etcd 无此功能.

# 六. Ribbon负载均衡
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

## 3. 原理

rest接口请求次数 % 服务器集群总数 = 实际调用服务器位置下标

CAS + 自旋锁

# 七. OpenFeign

## 1. Feign概述

Feign是一个声明式WebService客户端。使用Feign能让编写WebService客户端更加简单。

它的使用方式是定义一个服务接口然后在上面添加注释。Feign也支持可拔插式的编码器和解码器。SpringCloud对Feign进行了封装，使其支持了Spring MVC标注注解和HttpMessageConverters。Feign可以与Eureka和Ribbon组合使用以支持负载均衡

## 2. OpenFeign与Feign的区别

Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端，Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。Fegin的使用方式是：使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务

```xml
<dependency>
	<groupId>org.springframework,cloud</groupId>
	<artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

OpenFeign是SpringCloud在Feign的基础上支持了SpringMVC的朱姐，如@RequestMapping等等。OpenFeign的@FeignClient可以解析SpringMVC的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>apring-cloud-starter-openfeign</artifactId>
</dependency>
```

## 3. OpenFeign使用

OpenFeign使用在客户端

在客户端启动类上添加注解：@EnableFeignClients

在客户端建立service以及对应的服务提供者service接口，具体如下：

```java
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE") //服务提供者名称
public interface PaymentFeignService {

    @GetMapping(value = "/payment/get/{id}") //服务提供者Controller路由路径
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout") //服务提供者Controller路由路径
    String paymentFeignTimeout();
}

```

## 4. 超时设置

OpenFeign底层使用Ribbon，默认请求服务提供者提供的服务，超时时间为1秒

在客户端设置默认超时时间

```yaml
# 设置feign客户端超时时间（OpenFeign默认支持Ribbon）
ribbon:
  # 指的是建立连接所用的时间，适用于网络状况正常的情况下，两端连接所用的时间
  ReadTimeout: 5000
  # 指的是建立连接后从服务器读取到可用资源所用的时间
  ConnectTimeout: 5000
```

## 5. OpenFeign HTTP 日志输出

默认的四种日志显示

- NONE：默认的，不显示任何日志；
- BASIC：仅记录请求方法、URL、响应状态码以及执行时间；
- HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息；
- FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文以及元数据；

配置：

增加配置类：FeignConfig

```java
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
```

application.yml增加配置

```
logging:
  level:
    # feign日志以什么级别监控哪个接口
    com.tomato.springcloud.service.PaymentFeignService: debug
```

# 八. Hystrix断路器(已停更)

## 1. 概述

​	Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。

​	“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝），向调用方向返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要的占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。

## 2. 作用

服务降级

服务熔断

接近实时的监控

## 3. 官网资料 

http://github.com/Netfix/Hystrix/wiki/How-To-Use

## 4.Hystrix重要概念

- 服务降级

  比如服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback

  哪些情况会触发降级

  1. 程序运行异常
  2. 超时
  3. 服务熔断触发服务降级
  4. 线程池/信号量打满也会导致服务降级

- 服务熔断

  类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示

  就是保险丝：服务的降级 -> 进而熔断 -> 恢复调用链路

- 服务限流

  秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行