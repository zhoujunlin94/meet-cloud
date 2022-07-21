package com.you.meet.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhoujunlin
 * @date 2022年06月09日 17:02
 * @desc
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class AppMemCache {

    public static void main(String[] args) {
        SpringApplication.run(AppMemCache.class, args);
    }

}
