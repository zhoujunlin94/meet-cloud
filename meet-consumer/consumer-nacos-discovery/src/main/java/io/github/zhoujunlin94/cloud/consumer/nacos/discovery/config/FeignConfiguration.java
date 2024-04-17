package io.github.zhoujunlin94.cloud.consumer.nacos.discovery.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhoujunlin
 * @date 2023年12月27日 11:23
 * @desc
 */
@Configuration
@EnableFeignClients(basePackages = {"io.github.zhoujunlin94.cloud.consumer.nacos.discovery.feign"})
public class FeignConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
