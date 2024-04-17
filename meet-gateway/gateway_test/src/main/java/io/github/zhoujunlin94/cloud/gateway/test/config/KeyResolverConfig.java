package io.github.zhoujunlin94.cloud.gateway.test.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 22:49
 * @desc
 */
@Configuration
public class KeyResolverConfig {

    @Bean
    public KeyResolver hostNameKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}
