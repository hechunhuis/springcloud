package com.tomato.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/22 10:46
 * @className : PaymentMainHystrix8001
 * @description: 断路器
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //Hystrix添加注解，开启服务降级配置
public class PaymentMainHystrix8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMainHystrix8001.class, args);
    }

    /**
     * 此配置是为了服务监控而配置，与服务容错本身无关，Springcloud升级的坑
     * ServletRegistrationBean因为SpringBoot的默认路径不是“/hystrix.stream",
     * 只要在自己的项目里配置上下面的servlet就可以
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
