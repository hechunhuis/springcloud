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
@RefreshScope
public class ConfigClientController {

    //config.info为读取到github的配置文件，文件中的变量
    @Value("${config.info}")
    private String configInfo;

    @Value("${config.version}")
    private String configVersion;

    @GetMapping("/getConfig")
    private String getConfigInfo() {
        return configInfo;
    }
}
