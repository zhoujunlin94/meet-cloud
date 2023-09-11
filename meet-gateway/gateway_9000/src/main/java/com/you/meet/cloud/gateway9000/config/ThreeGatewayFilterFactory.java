package com.you.meet.cloud.gateway9000.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author zhoujunlin
 * @date 2023年09月11日 21:44
 * @desc
 */
@Slf4j
@Component
public class ThreeGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            // filter pre
            log.info("three filter pre: {}-{}", config.getName(), config.getValue());

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        log.info("three filter post: {}-{}", config.getName(), config.getValue());
                    })
            );

        };
    }

}
