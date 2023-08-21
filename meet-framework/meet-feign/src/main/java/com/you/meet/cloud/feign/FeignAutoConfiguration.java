package com.you.meet.cloud.feign;

import com.you.meet.cloud.common.util.RequestIdUtil;
import feign.RequestInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhoujunlin
 * @date 2023年02月20日 10:37
 * @desc
 */
@ComponentScan
public class FeignAutoConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RequestInterceptor feignRequestIdInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(RequestIdUtil.REQUEST_ID, RequestIdUtil.getRequestId());
        };
    }

}