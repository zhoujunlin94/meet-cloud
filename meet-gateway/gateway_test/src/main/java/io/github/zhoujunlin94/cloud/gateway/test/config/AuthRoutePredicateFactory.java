package io.github.zhoujunlin94.cloud.gateway.test.config;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 10:08
 * @desc 自定义路由断言工厂   类名必须以RoutePredicateFactory为后缀 前缀为配置文件中的断言这里为Auth
 */
@Component
public class AuthRoutePredicateFactory extends AbstractRoutePredicateFactory<AuthRoutePredicateFactory.Config> {

    public AuthRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
            // 编写自己的代码逻辑 passwords:请求头里的配置  config.getPassword():配置文件的配置
            List<String> passwords = headers.get(config.getUsername());
            return CollUtil.contains(passwords, config.getPassword());
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 配置文件中的字段顺序
        return Arrays.asList("username", "password");
    }


    @Data
    public static class Config {
        private String username;
        private String password;
    }

}
