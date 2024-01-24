package com.you.meet.cloud.gateway.test.config;

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
public class OneGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            // filter pre
            long startTime = System.currentTimeMillis();
            log.info("one filter pre: {}-{}, startTime:{}", config.getName(), config.getValue(), startTime);
            exchange.getAttributes().put("startTime", startTime);

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        long endTime = System.currentTimeMillis();
                        log.info("one filter post: {}-{}, endTime:{}", config.getName(), config.getValue(), endTime);
                        Long preStartTime = (Long) exchange.getAttributes().get("startTime");
                        long elapsedTime = endTime - preStartTime;
                        log.warn("one filter elapsedTime:{}ms", elapsedTime);
                    })
            );

        };
    }

}
