package io.github.zhoujunlin94.cloud.consumer.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author zhoujunlin
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "TOPIC_DEMO09",
        consumerGroup = "TOPIC_DEMO09-consumer-group",
        selectorType = SelectorType.SQL92,
        selectorExpression = "age >= 20 and sex = 'unknown'"
)
public class Demo09Consumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        // SQL消息  只执行了StudentC的数据  但其实三个消息都消费了CONSUMED
        log.info("[Demo09Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), messageStr);
    }

}
