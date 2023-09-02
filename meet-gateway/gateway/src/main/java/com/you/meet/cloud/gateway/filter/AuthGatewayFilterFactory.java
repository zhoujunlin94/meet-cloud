package com.you.meet.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.common.pojo.JSONResponse;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年03月16日 17:21
 * @desc
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    public AuthGatewayFilterFactory() {
        super(AuthGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // <1> token 和 userId 的映射
        Map<String, Integer> tokenMap = new HashMap<>();
        tokenMap.put("yunai", 1);

        // 创建 GatewayFilter 对象
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // <2> 获得 token
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                String token = headers.getFirst(config.getTokenHeaderName());

                // <3> 如果没有 token，则不进行认证。因为可能是无需认证的 API 接口
                if (StrUtil.isBlank(token)) {
                    return chain.filter(exchange);
                }

                // <4> 进行认证
                ServerHttpResponse response = exchange.getResponse();
                Integer userId = tokenMap.get(token);

                // <5> 通过 token 获取不到 userId，说明认证不通过
                if (Objects.isNull(userId)) {
                    // 响应 401 状态码
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    // 响应提示
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(JSONResponse.fail("认证不通过").toString().getBytes());
                    return response.writeWith(Flux.just(buffer));
                }

                // <6> 认证通过，将 userId 添加到 Header 中
                request = request.mutate().header(config.getUserIdHeaderName(), String.valueOf(userId))
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            }

        };
    }


    @Data
    public static class Config {

        private static final String DEFAULT_TOKEN_HEADER_NAME = "token";
        private static final String DEFAULT_HEADER_NAME = "user-id";

        private String tokenHeaderName = DEFAULT_TOKEN_HEADER_NAME;
        private String userIdHeaderName = DEFAULT_HEADER_NAME;

    }


}
