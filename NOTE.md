

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

# 三. Eureka(服务注册中心)（已停更）

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

# 四. Zookeeper(服务注册中心)

ZooKeeper是一个[分布式](https://baike.baidu.com/item/%E5%88%86%E5%B8%83%E5%BC%8F/19276232?fromModule=lemma_inlink)的，开放源码的[分布式应用程序](https://baike.baidu.com/item/%E5%88%86%E5%B8%83%E5%BC%8F%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F/9854429?fromModule=lemma_inlink)协调服务，是[Google](https://baike.baidu.com/item/Google?fromModule=lemma_inlink)的Chubby一个[开源](https://baike.baidu.com/item/%E5%BC%80%E6%BA%90/246339?fromModule=lemma_inlink)的实现，是Hadoop和[Hbase](https://baike.baidu.com/item/Hbase/7670213?fromModule=lemma_inlink)的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。

ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。

ZooKeeper包含一个简单的原语集，提供Java和C的接口。

ZooKeeper代码版本中，提供了分布式独享锁、选举、队列的接口，代码在$zookeeper_home\src\recipes。其中分布锁和队列有[Java](https://baike.baidu.com/item/Java/85979?fromModule=lemma_inlink)和C两个版本，选举只有Java版本。

# 五. Consul(服务注册中心)

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

# 六. Ribbon(负载均衡)
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

# 七. OpenFeign(服务调用)

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







### 服务熔断（服务端）

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

#### 工作流程

1. 创建HystrixCommand（用在依赖的服务返回单个操作结果的时候）或HystrixObserableCommand（用在依赖的服务返回多个操作结果的时候）对象
2. 命令执行，其中HystrixCommand实现了下面前两种执行方式；而HystrixObservableCommand实现了后两种方式；execute（）；同步执行，从以来的服务返回一个单一的结果对象，或是在发生错误的时候抛出异常，queue()；异步执行，直接返回一个Future对象，其中包含了服务执行结束时要返回的单一结果对象，observe();返回Observable对象，他代表了操作的多个结果，他是一个Hot Obserable（不论“事件源”是否有“订阅者”，都会在创建后对事件进行发布，所以对于Hot Observable的每一个“订阅者”都有可能是从“事件源"的中途开始的，并可能只是看到了整个操作的局部过程），ToObservable()；同样会返回Observable对象，也代表了操作的多个结果，但它返回的是一个Cold Observable（没有”订阅者“的时候并不会发布事件，而是进行等待，直到有“订阅者”之后才发布事件，所以对于Cold Observable的订阅者，他可以保证从一开始看到整个操作的全部过程）。
3. 若当前命令的请求缓存功能是被启用的，并且该命令缓存命中，那么缓存的结果会立即以Observable对象的形式返回。
4. 检查断路器是否为打开状态，如果断路器是打开的，那么Hystrix不会执行命令，而是转接到fallback 处理逻辑（第8步）；如果断路器是关闭的，检查是否有可用资源来执行命令（第5步）。
5. 线程池/请求队列/信号量是否占满，如果命令依赖服务的专有线程池和请求队列，或者信号量（不使用线程池的时候）已经被占满，那么Hystrix也不会执行命令，而是转移到fallback处理逻辑（第8步）
6. Hystrix会根据我们编写的方法来决定采取什么样的方式去请求依赖服务，HystrixCommand.run()；返回一个单一的结果，或者抛出异常，HystrixObservableCommand.construct()；返回一个Observable对象来发射多个结果，或通过onError发送错误通知。
7. Hystrix会将“成功”、“失败”、“拒绝”、“超时”等信息报告给断路器，而断路器会维护一组计数器来统计这些数据，断路器会使用这些统计数据来决定是否要将断路器打开，来对某个依赖服务的请求进行“熔断/短路”。
8. 当命令执行失败的时候，Hystrix会进入fallback尝试回退处理，我们通常也称该操作为“服务降级”，而能够引起服务降级处理的情况有下面几种：第4步：当前命令处于“熔断/短路”状态，断路器是打开的时候，第5步：当前命令的线程池、请求队列或者信号量被占满的时候，第6步：HystrixObservableCommand。construct()或HystrixCommand.run()抛出异常的时候。
9. 当Hystrix命令执行成功之后，他会将处理结果直接返回或是以Observable的形式返回。

tips：如果我们没有为命令实现降级逻辑或者在降级处理中抛出了异常，Hystrix依然会返回一个Observable对象，但是它不会发射任何结果数据，而是通过onERROR方法通知命令立即中断请求，并通过onError()方法将引起命令失败的异常发送给调用者。

### 服务限流

秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行

### 图形化面板

# 九. Zuul&Zuul2(服务网关)



# 十. GateWay(服务网关)

## 1. 概述简介

#### 是什么？

springcloud gateway 是SpringCloud的一个全新的项目，基于Spring 5.0 + Spring Boot 2.0和Project Reactor等技术开发的网关，它旨在为微服务架构提供一种简单有效的统一的API路由管理方式。

**SpringCloud Gateway 作为SpringCloud生态系统中的网关，目标是代替Zuul，在SpringCloud 2.0 以上版本中，没有对新版本的Zuul 2.0以上最新高性能版本进行集成，仍然还是使用的Zuul 1.x非Reactor模式的老版本。而为了 提升网关的性能，SpringCloud Gateway是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty。**

SpringCloud Gateway的目标通过统一的路由方式且基于Filter链的方式提供了网关基本的功能，例如：安全，监控/指标，和限流。

#### 能干嘛？

反向代理

鉴权

流量控制

熔断

日志监控

#### 微服务架构中网关在哪里

外部请求(手持终端、Html5、Open接口) -> 负载均衡 -> **网关** -> 微服务

#### 有Zuul了怎么又出来了gateway

##### 为什么选择Gateway

1. Gateway是基于**异步非阻塞模型**上进行开发的，性能方面不需要担心。虽然Netfix早就发布了最新的Zuul2.x，但Spring Cloud貌似没有整合计划。而且Netflix相关组件都宣布进入维护期。

2. SpringCloud具有一下特性：

   **基于Spring Framework5，Project Reactor和Springboot 2.0 进行构建**

   **动态路由：能够匹配任何请求属性；**

   **可以对路由指定Predicate（断言）和Filter（过滤器）**

   **集成Hystrix的断路器功能**

   **集成SpringCloud服务发现功能；**

   **易于编写的Predicate（断言）和Filter（过滤器）；**

   **请求限流功能；**

   **支持路径重写。**

3. SpringCloud Gateway与Zuul的区别

   

#### Zuul1.x模型

#### GateWay模型：WebFlux是什么

​	在servlet3.1之后有了**异步非阻塞**的支持。而WebFlux是一个典型非阻塞异步的框架，它的核心是基于Reactor的相关API实现的。相对于传统的Web框架来说，它可以运行在诸如Netty，Undertow以及支持Servlet3.1的容器上。非阻塞式+函数式编程(Spring5必须让你使用Java8)

## 2. 三大核心概念

### Route（路由）

路由是构建网关的基本模块，它由ID、目标URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由

### Predicate（断言）

参考的是Java8的java.util.function.Predicate

开发人员可以匹配HTTP请求中的所有内容（例如请求头或请求参数），如果请求与断言相匹配则进行路由

### Filter（过滤）

指的是Spring框架中GatewayFilter的实例，使用过滤器，可以再请求被路有前或者之后对请求进行修改。

### 总体

web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制。

Predicate就是我们的匹配条件；

Filter可以理解为一个无所不能的拦截器，有了这两个元素，再加上目标URI，就可以实现一个具体的路由了

## 3. GateWay工作流程

流程：Gateway Client —> SpringCloudGateway【（Gateway Handler Mapping）->（Gateway Web handler

）->（Filter|Filter|Filter|Proxy Filter）】—>Proxied Service

客户端向SpringCloudGateway发出请求。然后在Gateway Handler Mapping中找到与请求相匹配的路由，将其发送到Gateway Web handler.

Handler再通过指定的过滤链来将请求发送到我们实际的服务执行业务逻辑，然后返回。

过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前（“pre"）或之后（”post"）执行业务逻辑。

Filter在“pre”类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等，

在“post”类型的过滤器中可以做响应内容、响应头的修改，日志的输出，流量监控等有着非常重要的作用。

## 4. 入门配置

#### pom依赖

```xml
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
```

#### yml配置

```yaml
server:
  port: 9527
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      routes:
        - id: cloud_provider_payment_8001_get # 路由ID，没有固定ID，但要保证唯一
          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/** # 断言，路径相匹配的进行路由

        - id: cloud_provider_payment_8001_lb # 路由ID，没有固定ID，但要保证唯一
          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/** # 断言，路径相匹配的进行路由
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
  instance:
    instance-id: ${spring.application.name}-${server.port}
```

#### 启动类配置

```java
package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/6 13:12
 * @className : GateWayMain9527
 * @description: TODO
 */
@SpringBootApplication
@EnableEurekaClient
public class GateWayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWayMain9527.class, args);
    }
}

```

### 配置断言、路由的两种方式

#### yml

```yaml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      routes:
        - id: cloud_provider_payment_8001_get # 路由ID，没有固定ID，但要保证唯一
          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/** # 断言，路径相匹配的进行路由

        - id: cloud_provider_payment_8001_lb # 路由ID，没有固定ID，但要保证唯一
          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/** # 断言，路径相匹配的进行路由
```



#### 代码中注入RouteLocator的Bean

```java
package com.tomato.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/6 13:41
 * @className : GateWayConfig
 * @description: TODO
 */
@Configuration
public class GateWayConfig {

    private final String ROUTE_ID_GUONEI = "route_path_baidu_guonei";
    private final String ROUTE_ID_GAME = "route_path_baidu_game";

    private final String LOCAL_PATH_GUONEI = "guonei";
    private final String LOCAL_PATH_GAME = "game";
    private final String ROUTE_PATH = "https://news.baidu.com";

    @Bean
    public RouteLocator customRouteLocatorGuoNei(RouteLocatorBuilder builder) {
        // 访问localhost:port/guonei -> https://news.baidu.com/guonei
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route(ROUTE_ID_GUONEI,
                r -> r.path("/" + LOCAL_PATH_GUONEI).uri(ROUTE_PATH + "/" + LOCAL_PATH_GUONEI))
                .build();
        return routes.build();
    }

    @Bean
    public RouteLocator customRouteLocatorGame(RouteLocatorBuilder builder) {
        // 访问localhost:port/game -> https://news.baidu.com/game
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route(ROUTE_ID_GAME,
                r -> r.path("/" + LOCAL_PATH_GAME).uri(ROUTE_PATH + "/" + LOCAL_PATH_GAME))
                .build();
        return routes.build();
    }
}
```



## 5. 通过微服务名实现动态路由

```yaml
server:
  port: 9527
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true # 开启从注册中心动态创建路由功能，利用微服务名进行路由
      routes:
        - id: cloud_provider_payment_8001_get # 路由ID，没有固定ID，但要保证唯一
#          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          uri: lb://cloud-payment-service
          predicates:
            - Path=/payment/get/** # 断言，路径相匹配的进行路由

        - id: cloud_provider_payment_8001_lb # 路由ID，没有固定ID，但要保证唯一
#          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          uri: lb://cloud-payment-service
          predicates:
            - Path=/payment/lb/** # 断言，路径相匹配的进行路由
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
  instance:
    instance-id: ${spring.application.name}-${server.port}

```



## 6. Predicate的使用

#### 时间字符串获取方法：

```java
public class Test {
    public static void main(String args[]) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now()
		System.out.println(zonedDateTime);
    }
}
```

#### Route Predicate

指明在某个**时间点之后**才能被访问

```yaml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true # 开启从注册中心动态创建路由功能，利用微服务名进行路由
      routes:
        - id: cloud_provider_payment_8001_get # 路由ID，没有固定ID，但要保证唯一
#          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          uri: lb://cloud-payment-service
          predicates:
          	# Path Route Predicate，断言，路径相匹配的进行路由
            - Path=/payment/get/** 
            # After Route Predicate，指定在某个时间后才能访问，否则报404
            - After=2022-11-09T18:10:00.485+08:00[Asia/Shanghai] 
            # Before Route Predicate，指定在某个时间前才能访问，否则报404
            - Before=2022-11-09T18:10:00.485+08:00[Asia/Shanghai] 
            # Between Route Predicate，指定在某个时间之间才能访问，否则报404
            - Between=2022-11-09T18:10:00.485+08:00[Asia/Shanghai],2022-11-09T18:10:00.485+08:00[Asia/Shanghai] 
            # Cookie Route Predicate，参数：[cookiename，正则表达式]
            - Cookie=username,zzz
            # Header Route Predicate,参数：[属性名,正则表达式]
            - Header=X-Request-Id, \d+
            # Host Route Predicate,参数：[正则表达式]
            - Host=***.tomato.com
            # Method Route Predicate,参数：[请求方式]
            - Method=get
            # Query Route Predicate 根据查询条件，需要满足{url}?key=value,value支持正则表达式
            - Query=name,\d+
```

## 7. Filter的使用

官网：https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1RELEASE/reference/html/#the-addrequestparameter-gatewayfilter-factory

使用过滤器，可以在请求被路由前或者之后对请求进行修改

路由过滤器可用于修改进入的http请求和返回的http响应，路由过滤器只能指定路由进行使用

SpringCloud Gateway内置了多种路由过滤器，他们都由GatewayFilter的工厂类生产

**生命周期**：pro（之前）、post（之后）

**种类**:GatewayFilter（单一的）、GlobalFilter（全局）

```yaml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          # 开启从注册中心动态创建路由功能，利用微服务名进行路由
          enabled: true 
      routes:
      	# 路由ID，没有固定ID，但要保证唯一
        - id: cloud_provider_payment_8001_get 
          uri: lb://cloud-payment-service
          filters:
          	#过滤器工厂会在匹配的请求头，加上一对请求头，名称为X-Request-Id，值为1024
          	- AddRequestParameter=X-Request-Id,1024 
```

#### 自定义全局过滤器

可以做全局日志记录、统一网关鉴权

```java
package com.tomato.springcloud.filter;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/9 22:43
 * @className : MyLogGatewayFilter
 * @description: 自定义全局过滤器
 */
@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("************* come in MyLogGatewayFilter: " + new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (StringUtil.isNullOrEmpty(uname)) {
            log.info("******** 用户名为null,非法用户，o(╥﹏╥)o");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
```



# 十一. SpringCloud Config分布式配置中心

## 1. 概述

SpringCloud Config 为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置

SpringCloud Config分为**服务端和客户端**两部分。

服务端也称为**分布配置中心，它是一个独立的微服务应用**，用来连接配置服务器并未客户提供获取配置信息，加密/解密信息等访问接口

客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用Git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过Git客户端工具来方便的管理和访问配置内容。

## 2. 功能和作用

- 集中管理配置文件
- 不同环境不通配置，动态化的配置更新，分环境部署，比如dev/test/prod/beta/release
- 运行期间动态调整配置，不需要在每个服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自己的信息
- 当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
- 将配置信息以REST接口的形式暴露

## 3. 官网

https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.2.1.RELEASE/reference/html/

## 4. Config服务端配置与测试

#### pom.xml配置

```yaml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloud</artifactId>
        <groupId>com.tomato.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-config-center-3344</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>com.tomato.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

#### application.yml

```yaml
server:
  port: 3344


spring:
  application:
    name: cloud-config-center
  cloud:
    config:
      server:
        git:
          uri: https://github.com/hechunhuis/springcloud-config.git # github上的仓库地址
          # 搜索目录
          search-paths:
            - springcloud-config
      # 读取的分支
      label: master

# 服务注册到eureka地址
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
```

#### 启动类

```java
package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/10 23:31
 * @className : CloudCenterMain3344
 * @description: TODO
 */
@SpringBootApplication
@EnableConfigServer
public class CloudCenterMain3344 {
    //增加host文件配置 127.0.0.1 config-3344.com
    public static void main(String[] args) {
        SpringApplication.run(CloudCenterMain3344.class, args);
    }
}

```

#### 测试

启动7001服务注册中心

启动config3344服务端

浏览器访问：http://localhost:3344/master/config-dev.yml

## 5. Config客户端配置与测试

#### bootstrap.yml与application.yml的区别

application.yml是用户级的资源配置项

bootstrap.yml是系统级的，优先级更高



SpringCloud 会创建一个“Bootstrap Context”，作为Spring应用的“Application Context”的父上下文。初始化的时候，“Bootstrap Context”负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的“Environment”。

“Bootstrap”属性有高优先级，默认情况下，他们不会被本地配置覆盖。“Bootstrap Context”和“Application Context”有着不同的约定，所以新增一个“bootstrap.yml”文件，保证“Bootstrap Context”和“Application Context”配置的分离

**要将Client模块下的application.yml文件改为bootstrap.yml**，这是很关键的；

因为bootstrap.yml要比application.yml先加载的，bootstrap.yml优先级高于application.yml

#### pom.xml配置

```yaml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloud</artifactId>
        <groupId>com.tomato.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-config-client-3355</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>com.tomato.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

#### bootstrap.yml配置

```yaml
server:
  port: 3355
spring:
  application:
    name: config-client-3355
  cloud:
    config:
      label: master # 读取的分支
      name: config # 文件配置前缀
      profile: dev # 文件配置后缀
      uri: http://localhost:3344 # config服务端

# 服务注册到eureka地址
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka

```

#### 启动类

```java
package com.tomato.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/11 18:35
 * @className : ConfigClientMain3355
 * @description: TODO
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientMain3355 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3355.class, args);
    }
}

```

#### 路由测试类

```
package com.tomato.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/11 18:45
 * @className : ConfigClientController
 * @description: TODO
 */
@RestController
@Slf4j
public class ConfigClientController {

    //server.port为bootstrap.yml中配置读取到github的文件，文件中的变量
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/server/port")
    private String getConfigInfo() {
        return serverPort;
    }
}

```

## 6. Config客户端动态刷新之手动版

#### 客户端POM引入actuator监控

```yaml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

#### bootstrap.yml增加暴露监控端口

```yaml
# 暴露监控端口 解决config服务端刷新，客户端需要重启刷新问题
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

#### 增加@RefreshScope业务类Controller修改

```java
package com.tomato.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/11/11 18:45
 * @className : ConfigClientController
 * @description: TODO
 */
@RestController
@Slf4j
@RefreshScope //增加注解
public class ConfigClientController {

    //server.port为读取到github的配置文件，文件中的变量
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/server/port")
    private String getConfigInfo() {
        return serverPort;
    }
}

```

#### 注意

**配置后需要手动发送POST请求：curl -X POST "http://localhost:3355/actuator/refresh"，来刷新3355客户端的配置**

**自动刷新参考SpringCloud Bus 消息总线**

# 十二. SpringCloud Bus 消息总线

## 1.概述

**是什么：**Bus支持两种消息代理：RabbitMQ和kafka

**能干嘛：**能管理和传播分布式系统间的消息，就像一个分布式执行器，可用于广播状态更改、事件推送等，也可以当作微服务间的通信通道

**什么是总线：**在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播一些需要让其他连接在该主题上的实例都知道的消息。

**基本原理：**ConfigClient实例都监听MQ中同一个topic（默认是SpringCloudBus）。当一个服务刷新数据的时候，他会把这个信息放入topic中，这样其他监听同一Topic的服务就能得到通知，然后去更新自身的配置。

## 2. RabbitMQ环境配置

1. 安装docker desktop，下载地址：https://www.docker.com/products/docker-desktop/

2. 拉取rabbitmq镜像，命令：docker pull rabbitmq:3.8.0-beta.4-management（management是带有UI界面的）

3. 启动rabbitmq容器，命令：

   docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.8.0-beta.4-management 

4. 测试访问成功：http://localhost:15672

5. 输入账号密码：guest guest

## 3. SpringCloud Bus动态刷新全局广播

## 4. SpringCloud Bus动态刷新定点通知