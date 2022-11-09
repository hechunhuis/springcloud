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
