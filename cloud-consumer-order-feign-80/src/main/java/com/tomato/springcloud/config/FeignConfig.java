package com.tomato.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/20 23:03
 * @className : FeignConfig
 * @description: OpenFeign日志输出
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
