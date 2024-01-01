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
public class Demo04Consumer {

    @KafkaListener(
            topics = {"TOPIC_DEMO04"},
            // 通过 Spring EL 表达式，在每个消费者分组的名字上，使用 UUID 生成其后缀。这样，我们就能保证每个项目启动的消费者分组不同，以达到广播消费的目的。
            groupId = "TOPIC_DEMO04-consumer-group-" + "#{T(java.util.UUID).randomUUID()}"
    )
    public void onMessage(String message) {
        log.info("[Demo04Consumer.onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
