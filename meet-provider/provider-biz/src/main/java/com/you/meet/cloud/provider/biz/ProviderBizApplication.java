package com.you.meet.cloud.provider.biz;

import com.you.meet.cloud.provider.biz.message.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableDiscoveryClient
@EnableBinding(MySource.class)
@SpringBootApplication
public class ProviderBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderBizApplication.class, args);
    }

}
