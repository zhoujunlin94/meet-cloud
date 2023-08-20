package com.you.meet.cloud.consumer8080;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhoujunlin
 * @date 2023年08月17日 22:59
 * @desc
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class Consumer8080Application {

    public static void main(String[] args) {
        SpringApplication.run(Consumer8080Application.class);
    }

}
