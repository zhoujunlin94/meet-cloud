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
        topic = "TOPIC_DEMO08",
        consumerGroup = "TOPIC_DEMO08-consumer-group",
        selectorType = SelectorType.TAG,
        selectorExpression = "TAGA || TAGC"
)
public class Demo08Consumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        // TAG消息   CONSUMED_BUT_FILTERED(TAGB)   CONSUMED(TAGA  TAGC)
        log.info("[Demo08Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), messageStr);
    }

}
