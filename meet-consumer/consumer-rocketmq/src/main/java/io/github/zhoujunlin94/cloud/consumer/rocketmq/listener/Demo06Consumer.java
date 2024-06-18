package io.github.zhoujunlin94.cloud.consumer.rocketmq.listener;

import io.github.zhoujunlin94.cloud.consumer.rocketmq.dto.BaseMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujunlin
 * @date 2023年01月03日 13:36
 * @desc
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "TOPIC_DEMO06",
        consumerGroup = "TOPIC_DEMO06-consumer-group",
        // 设置为顺序消费 (单线程消费-保证有序   默认多线程消费)
        consumeMode = ConsumeMode.ORDERLY
)
public class Demo06Consumer implements RocketMQListener<BaseMessageDTO> {

    @Override
    public void onMessage(BaseMessageDTO message) {
        log.info("[Demo06Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
        try {
            // sleep 2s 用于查看顺序消费效果
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
