package com.you.meet.cloud.gateway9000.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author zhoujunlin
 * @date 2023年09月11日 21:44
 * @desc
 */
@Component
public class AddHeaderGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest()
                    .mutate().header(config.getName(), config.getValue())
                    .build();

            ServerWebExchange webExchange = exchange.mutate().request(serverHttpRequest).build();
            return chain.filter(webExchange);
        };
    }

}
