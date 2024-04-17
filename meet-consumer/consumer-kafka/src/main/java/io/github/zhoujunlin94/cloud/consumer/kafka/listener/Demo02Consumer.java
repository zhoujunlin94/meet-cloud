package io.github.zhoujunlin94.cloud.consumer.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2022年12月26日 21:35
 * @desc
 */
@Slf4j
@Component
public class Demo02Consumer {

    /**
     * 单个消息消费
     */
    /*@KafkaListener(
            topics = {"TOPIC_DEMO02"},
            groupId = "TOPIC_DEMO02-consumer-group"
    )
    public void onMessage(String message) {
        log.info("[Demo02Consumer.onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }*/

    /**
     * 批量消费
     *
     * @param messages
     */
    @KafkaListener(
            topics = {"TOPIC_DEMO02"},
            groupId = "TOPIC_DEMO02-consumer-group"
    )
    public void onMessage(List<String> messages) {
        log.info("[Demo02Consumer.onMessage][线程编号:{},消息数量:{} 消息内容:{}]", Thread.currentThread().getId(), messages.size(), messages);
    }

}


