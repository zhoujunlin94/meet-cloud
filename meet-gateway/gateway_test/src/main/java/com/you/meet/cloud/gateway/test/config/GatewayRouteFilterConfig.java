package com.you.meet.cloud.gateway.test.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 21:28
 * @desc 对于相同 filter 工厂，在不同位置设置了不同的值，则优先级为
 * 1. 局部 filter 的优先级高于默认 filter 的
 * 2. API 式的 filter 优先级高于配置式 filter 的  这点与路由断言工厂恰恰相反
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

    @Bean
    public RouteLocator prefixPathFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("prefixPath", ps ->
                        ps.path("/headers")
                                .filters(fs -> fs.prefixPath("/info"))
                                .uri("http://localhost:8980")
                ).build();
    }

    @Bean
    public RouteLocator stripPrefixFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("prefixPath", ps ->
                        ps.path("/a/b/info/**")
                                .filters(fs -> fs.stripPrefix(2))
                                .uri("http://localhost:8980")
                ).build();
    }

    @Bean
    public RouteLocator rewritePathFilter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rewritePath", ps ->
                        ps.path("/a/b/**")
                                .filters(fs -> fs.rewritePath("/a/b", "/info"))
                                .uri("http://localhost:8980")
                ).build();
    }


}
