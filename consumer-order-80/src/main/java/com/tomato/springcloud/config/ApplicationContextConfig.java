package com.tomato.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/8 20:59
 * @className : ApplicationContextConfig
 * @description: TODO
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
