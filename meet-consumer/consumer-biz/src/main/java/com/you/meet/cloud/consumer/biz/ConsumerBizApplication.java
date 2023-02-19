package com.you.meet.cloud.consumer.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBizApplication.class, args);
    }

}
