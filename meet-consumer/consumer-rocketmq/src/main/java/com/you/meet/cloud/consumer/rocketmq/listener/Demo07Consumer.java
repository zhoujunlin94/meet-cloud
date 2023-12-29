package com.you.meet.cloud.consumer.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author zhoujunlin
 * @date 2023年01月03日 13:36
 * @desc
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "TOPIC_DEMO07",
        consumerGroup = "TOPIC_DEMO07-consumer-group"
)
public class Demo07Consumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        // 事务消息
        log.info("[Demo07Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), messageStr);
    }

}
