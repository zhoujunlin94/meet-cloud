package io.github.zhoujunlin94.cloud.consumer.rocketmq.listener;

import io.github.zhoujunlin94.cloud.consumer.rocketmq.dto.BaseMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年01月03日 13:36
 * @desc
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "TOPIC_DEMO03",
        consumerGroup = "TOPIC_DEMO03-consumer-group"
)
public class Demo03Consumer implements RocketMQListener<BaseMessageDTO> {

    @Override
    public void onMessage(BaseMessageDTO message) {
        log.info("[Demo03Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
    }

}
