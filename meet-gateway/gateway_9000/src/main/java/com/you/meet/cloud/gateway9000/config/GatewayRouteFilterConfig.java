package com.you.meet.cloud.gateway9000.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 21:28
 * @desc
 */
/*@Configuration*/
public class GatewayRouteFilterConfig {

    @Bean
    public RouteLocator addRequestHeaderFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("addRequestHeader", ps ->
                        ps.path("/**")
                                .filters(fs ->
                                        fs.addRequestHeader("X-Request-Color", "blue")
                                ).uri("http://localhost:8980")
                ).build();
    }

    @Bean
    public RouteLocator addRequestParameterFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("addRequestParameter", ps ->
                        ps.path("/**")
                                .filters(fs ->
                                        fs.addRequestParameter("color", "blue")
                                                .addRequestParameter("color", "red")
                                                .addRequestParameter("size", "123")
                                ).uri("http://localhost:8980")
                ).build();
    }

    @Bean
    public RouteLocator addResponseHeaderFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("addResponseHeader", ps ->
                        ps.path("/**")
                                .filters(fs ->
                                        fs.addResponseHeader("color", "blue")
                                                .addResponseHeader("color", "red")
                                                .addResponseHeader("username", "tom")
                                ).uri("http://localhost:8980")
                ).build();
    }

    @Bean
    public RouteLocator circuitBreakerFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("addResponseHeader", ps ->
                        ps.path("/info/**")
                                .filters(fs ->
                                        fs.circuitBreaker(config -> {
                                            config.setName("myCircuitBreaker");
                                            config.setFallbackUri("forward:/fb");
                                        })
                                ).uri("http://localhost:8980")
                ).build();
    }


}
