package io.github.zhoujunlin94.cloud.consumer.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2022年12月26日 21:35
 * @desc 集群消费模式下，相同 Consumer Group 的每个 Consumer 实例平均分摊消息
 * 如果我们发送一条 Topic 为 "DEMO_01" 的消息，可以分别被 "demo01-A-consumer-group-DEMO_01" 和 "demo01-consumer-group-DEMO_01" 都消费一次。
 * 但是，如果我们启动两个该示例的实例，则消费者分组 "demo01-A-consumer-group-DEMO_01" 和 "demo01-consumer-group-DEMO_01" 都会有多个 Consumer 示例。
 * 此时，我们再发送一条 Topic 为 "DEMO_01" 的消息，只会被 "demo01-A-consumer-group-DEMO_01" 的一个 Consumer 消费一次，
 * 也同样只会被 "demo01-A-consumer-group-DEMO_01" 的一个 Consumer 消费一次。
 */
@Slf4j
@Component
public class Demo01AConsumer {

    @KafkaListener(
            topics = {"TOPIC_DEMO01"},
            // errorHandler = "", 使用消费异常处理器 KafkaListenerErrorHandler 的 Bean 名字
            // concurrency = 3, 自定义消费者监听器的并发数
            // containerFactory = "", 使用的 KafkaListenerContainerFactory Bean 的名字。若未设置，则使用默认的 KafkaListenerContainerFactory Bean
            groupId = "TOPIC_DEMO01-A-consumer-group"
    )
    public void onMessage(String message) {
        log.info("[Demo01AConsumer.onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}


