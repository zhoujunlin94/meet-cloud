package com.you.meet.cloud.consumer.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2022年12月26日 21:35
 * @desc
 */
@Slf4j
@Component
public class Demo03Consumer {

    @KafkaListener(
            topics = {"TOPIC_DEMO03"},
            groupId = "TOPIC_DEMO03-consumer-group"
    )
    public void onMessage(String message) {
        log.info("[Demo03Consumer.onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // 模拟消费失败 重试
        throw new RuntimeException("我就故意抛出一个异常");
    }

}


