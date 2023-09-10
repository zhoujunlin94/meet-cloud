package com.you.meet.cloud.gateway9000.config;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 10:08
 * @desc 自定义路由断言工厂   类名必须以RoutePredicateFactory为后缀 前缀为配置文件中的断言这里为Token
 */
@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {

    public TokenRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            MultiValueMap<String, String> queryParams = serverWebExchange.getRequest().getQueryParams();
            String token = queryParams.getFirst("token");
            return config.getToken().equals(token);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 配置文件中的字段顺序
        return Collections.singletonList("token");
    }


    @Data
    public static class Config {
        private String token;
    }

}
