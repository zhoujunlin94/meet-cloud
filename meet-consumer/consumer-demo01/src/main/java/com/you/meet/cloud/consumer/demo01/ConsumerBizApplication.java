package com.you.meet.cloud.consumer.demo01;

import com.you.meet.cloud.consumer.demo01.message.MySink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(MySink.class)
@EnableFeignClients
public class ConsumerBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBizApplication.class, args);
    }

}
