package io.github.zhoujunlin94.cloud.consumer.rocketmq.listener;

import io.github.zhoujunlin94.cloud.consumer.rocketmq.dto.BaseMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年01月03日 13:36
 * @desc 建议一个消费者分组，仅消费一个 Topic 。这样做会有两个好处：
 * 1. 每个消费者分组职责单一，只消费一个 Topic 。
 * 2. 每个消费者分组是独占一个线程池，这样能够保证多个 Topic 隔离在不同线程池，保证隔离性，从而避免一个 Topic 消费很慢，影响到另外的 Topic 的消费。
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "TOPIC_DEMO01",
        //选择器类型。默认基于 Message 的 Tag 选择。
//        selectorType = SelectorType.TAG,
        //选择器的表达式。设置为 * 时，表示全部。如果使用 SelectorType.TAG 类型，则设置消费 Message 的具体 Tag 。
        //如果使用 SelectorType.SQL92 类型，可见 https://rocketmq.apache.org/rocketmq/filter-messages-by-sql92-in-rocketmq/ 文档
//        selectorExpression = "*",
        //消费模式。可选择并发消费，还是顺序消费。
//        consumeMode = ConsumeMode.CONCURRENTLY,
        //消息模型。可选择是集群消费，还是广播消费。  默认集群消费
//        messageModel = MessageModel.CLUSTERING,
        //消费的线程池的最大线程数
//        consumeThreadMax = 64,
        //消费单条消息的超时时间
//        consumeTimeout = 30000,
//        enableMsgTrace = true,
//        customizedTraceTopic =
//        nameServer =
        // 访问通道。目前有 LOCAL 和 CLOUD 两种通道。
        // LOCAL ，指的是本地部署的 RocketMQ 开源项目。
        // CLOUD ，指的是阿里云的 ONS 服务。具体可见 https://help.aliyun.com/document_detail/128585.html 文档。
//        accessChannel =
        consumerGroup = "TOPIC_DEMO01-consumer-group-A"
)
public class Demo01AConsumer implements RocketMQListener<BaseMessageDTO> {

    @Override
    public void onMessage(BaseMessageDTO message) {
        log.info("[Demo01AConsumer.onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
    }

}
