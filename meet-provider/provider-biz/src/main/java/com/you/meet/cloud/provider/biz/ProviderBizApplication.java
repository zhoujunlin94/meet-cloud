package com.you.meet.cloud.provider.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProviderBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderBizApplication.class, args);
    }

}
