package com.you.meet.cloud.gateway9000.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author zhoujunlin
 * @date 2023年09月05日 22:04
 * @desc 编程式网关   配置式网关与编程式网关在不冲突的情况下是【或】的关系  在冲突的情况下配置式网关优先级高于编程式网关
 */
/*@Configuration*/
public class GatewayConfig {

    @Bean
    public RouteLocator baseRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("jd_route", predicate ->
                        predicate.path("/**")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator afterRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        ZonedDateTime afterTime = LocalDateTime.of(2023, 9, 5, 22, 0, 0, 0)
                .atZone(ZoneId.systemDefault());
        ZonedDateTime beforeTime = LocalDateTime.of(2023, 9, 30, 22, 0, 0, 0)
                .atZone(ZoneId.systemDefault());
        return routeLocatorBuilder.routes()
                .route("after_route", predicate ->
//                        predicate.after(afterTime)
//                        predicate.before(beforeTime)
                        predicate.between(afterTime, beforeTime)
                                .uri("https://www.jd.com"))
                .build();
    }


    @Bean
    public RouteLocator cookieRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("cookie_route", predicate ->
                        predicate.cookie("city", "shanghai")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator headerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("header_route", predicate ->
                        predicate.header("X-Request-Id", "\\d+")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator hostRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("host_route", predicate ->
                        predicate.host("a.com:9000")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator methodRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("method_route", predicate ->
                        predicate.method("GET", "POST")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator pathRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("provider_route", predicate ->
                        predicate.path("/depart/**")
                                .uri("http://localhost:8991"))
                .route("consumer_route", predicate ->
                        predicate.path("/depart2/**")
                                .uri("http://localhost:8990"))
                .build();
    }

    @Bean
    public RouteLocator queryRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("query_route", predicate ->
                        predicate.query("color", "gr.+")
                                .and()
                                .query("size")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator remoteAddrRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("remoteAddr_route", predicate ->
                        predicate.remoteAddr("192.168.1.1/24")
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator weightAddrRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("taobao_route", predicate ->
                        predicate.weight("shop", 8)
                                .uri("https://www.taobao.com"))
                .route("jd_route", predicate ->
                        predicate.weight("shop", 2)
                                .uri("https://www.jd.com"))
                .build();
    }

    @Bean
    public RouteLocator forwardedRemoteAddrRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("forwardRemoteAddr_route", predicate ->
                        predicate.xForwardedRemoteAddr("192.168.1.1/24", "192.168.10.10")
                                .uri("https://www.jd.com"))
                .build();
    }

}
