package io.github.zhoujunlin94.cloud.consumer.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年01月03日 13:36
 * @desc 集群消费（Clustering）：集群消费模式下，相同 Consumer Group 的每个 Consumer 实例平均分摊消息。
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "TOPIC_DEMO01",
        consumerGroup = "TOPIC_DEMO01-consumer-group-B"
)
public class Demo01BConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        log.info("[Demo01BConsumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
    }

}
