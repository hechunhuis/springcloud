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
