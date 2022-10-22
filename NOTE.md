[TOC]



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



服务注册中心：[Eureka](#三. Eureka服务注册中心)【**×**】 -> [Zookeeper](#四. Zookeeper)【√】 -> [Consul](#五. Consul)【√】 -> Nacos【√】

服务调用：**[Ribbon](#六. Ribbon负载均衡)【√】 -> LoadBalancer【√】**

服务调用2：Feign【**×**】 -> **[OpenFeign](#七. OpenFeign)【√】**

服务降级：Hystrix【**×**】 -> **resilience4j 【√】-> sentinel【√】**

服务网关：Zuul 【**×**】-> Zuul2【！】 -> **gateway**【√】

服务配置：Config【**×**】 -> **Nacos**【√】

服务总线：Bus【**×**】 -> **Nacos【√】**

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

### 服务降级（一般配置在客户端）

比如服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback

哪些情况会触发降级

1. 程序运行异常
2. 超时
3. 服务熔断触发服务降级
4. 线程池/信号量打满也会导致服务降级

**maven依赖：**

```xml
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```

启动类添加

```java
@EnableHystrix
```

#### 自定义服务降级

在消费者controller的路由方法上添加一下代码，并添加指定fallback方法【paymentReturnTimeoutHandler】，设置超时1.5秒自动返回【paymentReturnTimeoutHandler】的处理

```java
@HystrixCommand(fallbackMethod = "paymentReturnTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
```

#### 全局服务降级

在Controller路由上添加默认属性

```java
@DefaultProperties(defaultFallback = "paymentGlobalFallback")
```

在路由中添加注解@HystrixCommand，并添加方法【paymentGlobalFallback】

```
public String paymentGlobalFallback() {
        return "服务器繁忙，请稍后再试o(╥﹏╥)o";
    }
```

#### FeignFallback解耦配置

在service接口上声明注解指定fallback的类

```
@FeignClient(value = "CLOUD-PROVIDER-PAYMENT-HYSTRIX", fallback = PaymentHystrixFallbackServiceImpl.class)
```

实现这个接口中的所有方法，实现方法为fallback的处理方法

```
@Component
public class PaymentHystrixFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentReturnOK(Integer id) {
        return "paymentReturnOK繁忙 o(╥﹏╥)o";
    }

    @Override
    public String paymentReturnTimeout(Integer id) {
        return "paymentReturnTimeout繁忙 o(╥﹏╥)o";
    }
}
```







### 服务熔断

#### 概述

类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示

就是保险丝：服务的降级 -> 进而熔断 -> 恢复调用链路

#### 熔断类型

​	打开：请求不再进行调用当前服务，内部设置时钟一般为MTTR（平均故障处理时间），当打开时长达到所设时钟则进入半熔断状态

​	关闭：熔断关闭不会对服务进行熔断

​	半开：部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断

#### 断路器在什么情况下开始起作用

**断路器的三个重要参数**

1. **快照时间窗**：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒。
2. **请求总数阈值**：在快照时间窗内，必须满足请求总数阈值才有资格熔断。默认为20，意味着在10秒内，如果该hystrix命令的调用次数不足20次，即使所有的请求都超时或其他失败原因，断路器都不会打开。
3. **错误百分比阈值**：当请求总数在快照时间窗内超过阈值，比如发生了30次调用，如果在这30次调用中，有15次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%阈值情况下，这时候就会将断路器打开。

#### 断路器开启或者关闭的条件

1. 当满足一定的阈值的时候（默认10秒内超过20个请求次数）
2. 当失败率达到一定的时候（默认10秒内超过50%的请求失败）
3. 到达以上阈值，断路器将会开启
4. 当开启的时候，所有请求都不会进行转发
5. 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发。如果成功，断路器会关闭，若失败，继续开启。重复4和5

#### 断路器打开之后

1. 再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback。通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果。

2. 原来的主逻辑要如何恢复呢？

   对于这一问题，hystrix也为我们实现了自动恢复功能。当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间窗内，降级逻辑是临时的成主逻辑，当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将会继续闭合，主逻辑恢复，如果这次请求依然有问题，断路器继续进入打开状态，休眠时间窗重新计时。

#### All配置

```
Command属性主要用来控制HystrixCommand命令的行为，它主要分下面的类别：

1、Execution：用来控制HystrixCommand.run()的执行
execution.isolation.strategy：该属性用来设置HystrixCommand.run()执行的隔离策略。默认为THREAD。
execution.isolation.thread.timeoutInMilliseconds：该属性用来配置HystrixCommand执行的超时时间，单位为毫秒。
execution.timeout.enabled：该属性用来配置HystrixCommand.run()的执行是否启用超时时间。默认为true。
execution.isolation.thread.interruptOnTimeout：该属性用来配置当HystrixCommand.run()执行超时的时候是否要它中断。
execution.isolation.thread.interruptOnCancel：该属性用来配置当HystrixCommand.run()执行取消时是否要它中断。
execution.isolation.semaphore.maxConcurrentRequests：当HystrixCommand命令的隔离策略使用信号量时，该属性用来配置信号量的大小。当最大并发请求达到该设置值时，后续的请求将被拒绝。

2、Fallback：用来控制HystrixCommand.getFallback()的执行
fallback.isolation.semaphore.maxConcurrentRequests：该属性用来设置从调用线程中允许HystrixCommand.getFallback()方法执行的最大并发请求数。当达到最大并发请求时，后续的请求将会被拒绝并抛出异常。
fallback.enabled：该属性用来设置服务降级策略是否启用，默认是true。如果设置为false，当请求失败或者拒绝发生时，将不会调用HystrixCommand.getFallback()来执行服务降级逻辑。

3、Circuit Breaker：用来控制HystrixCircuitBreaker的行为。
circuitBreaker.enabled：确定当服务请求命令失败时，是否使用断路器来跟踪其健康指标和熔断请求。默认为true。
circuitBreaker.requestVolumeThreshold：用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候，如果滚动时间窗（默认10秒）内仅收到19个请求，即使这19个请求都失败了，断路器也不会打开。
circuitBreaker.sleepWindowInMilliseconds：用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后，会将断路器设置为“半开”状态，尝试熔断的请求命令，如果依然时候就将断路器继续设置为“打开”状态，如果成功，就设置为“关闭”状态。
circuitBreaker.errorThresholdPercentage：该属性用来设置断路器打开的错误百分比条件。默认值为50，表示在滚动时间窗中，在请求值超过requestVolumeThreshold阈值的前提下，如果错误请求数百分比超过50，就把断路器设置为“打开”状态，否则就设置为“关闭”状态。
circuitBreaker.forceOpen：该属性默认为false。如果该属性设置为true，断路器将强制进入“打开”状态，它会拒绝所有请求。该属性优于forceClosed属性。
circuitBreaker.forceClosed：该属性默认为false。如果该属性设置为true，断路器强制进入“关闭”状态，它会接收所有请求。如果forceOpen属性为true，该属性不生效。

4、Metrics：该属性与HystrixCommand和HystrixObservableCommand执行中捕获的指标相关。
metrics.rollingStats.timeInMilliseconds：该属性用来设置滚动时间窗的长度，单位为毫秒。该时间用于断路器判断健康度时需要收集信息的持续时间。断路器在收集指标信息时会根据设置的时间窗长度拆分成多个桶来累计各度量值，每个桶记录了一段时间的采集指标。例如，当为默认值10000毫秒时，断路器默认将其分成10个桶，每个桶记录1000毫秒内的指标信息。
metrics.rollingStats.numBuckets：用来设置滚动时间窗统计指标信息时划分“桶”的数量。默认值为10。
metrics.rollingPercentile.enabled：用来设置对命令执行延迟是否使用百分位数来跟踪和计算。默认为true，如果设置为false，那么所有的概要统计都将返回-1。
metrics.rollingPercentile.timeInMilliseconds：用来设置百分位统计的滚动窗口的持续时间，单位为毫秒。
metrics.rollingPercentile.numBuckets：用来设置百分位统计滚动窗口中使用桶的数量。
metrics.rollingPercentile.bucketSize：用来设置每个“桶”中保留的最大执行数。
metrics.healthSnapshot.intervalInMilliseconds：用来设置采集影响断路器状态的健康快照的间隔等待时间。

5、Request Context：涉及HystrixCommand使用HystrixRequestContext的设置。
requestCache.enabled：用来配置是否开启请求缓存。
requestLog.enabled：用来设置HystrixCommand的执行和事件是否打印到日志的HystrixRequestLog中。
```



#### 服务提供者Service

```java
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
```



### 服务限流

秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行