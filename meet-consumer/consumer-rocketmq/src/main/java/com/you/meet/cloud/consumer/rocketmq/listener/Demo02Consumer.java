package com.you.meet.cloud.consumer.rocketmq.listener;

import com.you.meet.cloud.consumer.rocketmq.dto.BaseMessageDTO;
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
        topic = "TOPIC_DEMO02",
        consumerGroup = "TOPIC_DEMO01-consumer-group"
)
public class Demo02Consumer implements RocketMQListener<BaseMessageDTO> {

    @Override
    public void onMessage(BaseMessageDTO message) {
        // 生产者批量发送  消费者逐条消费
        log.info("[Demo02Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
    }

}
