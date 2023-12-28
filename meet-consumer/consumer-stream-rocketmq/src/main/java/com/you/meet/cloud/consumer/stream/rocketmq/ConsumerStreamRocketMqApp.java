package com.you.meet.cloud.consumer.stream.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @author zhoujunlin
 * @date 2023年12月28日 15:56
 * @desc
 */
@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
public class ConsumerStreamRocketMqApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerStreamRocketMqApp.class, args);
    }

}
