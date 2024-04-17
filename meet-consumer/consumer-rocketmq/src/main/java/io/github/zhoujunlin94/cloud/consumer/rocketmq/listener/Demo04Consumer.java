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
        topic = "TOPIC_DEMO04",
        consumerGroup = "TOPIC_DEMO04-consumer-group"
)
public class Demo04Consumer implements RocketMQListener<BaseMessageDTO> {

    @Override
    public void onMessage(BaseMessageDTO message) {
        log.info("[Demo04Consumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
        // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败  消息重新发送
        //throw new RuntimeException("我就是故意抛出一个异常");
    }

}
