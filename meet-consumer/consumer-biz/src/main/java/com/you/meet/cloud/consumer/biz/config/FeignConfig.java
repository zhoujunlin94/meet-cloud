package com.you.meet.cloud.consumer.biz.config;

import feign.RequestInterceptor;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhoujunlin
 * @date 2023年03月14日 17:19
 * @desc
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignHeaderInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.isNull(requestAttributes)) {
                return;
            }
        };
    }

}
