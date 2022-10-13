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
