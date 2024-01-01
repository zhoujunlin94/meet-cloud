package com.you.meet.cloud.consumer.kafka.listener;

import com.you.meet.nice.common.util.RequestIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2022年12月26日 21:35
 * @desc 建议一个消费者分组，仅消费一个 Topic 。这样做会有个好处：每个消费者分组职责单一，只消费一个 Topic 。
 */
@Slf4j
@Component
public class Demo01BConsumer {

    @KafkaListener(topics = "TOPIC_DEMO01",
            groupId = "TOPIC_DEMO01-B-consumer-group")
    public void onMessage(ConsumerRecord<Integer, String> record) {
        String requestId = new String(record.headers().lastHeader(RequestIdUtil.REQUEST_ID).value());
        MDC.put(RequestIdUtil.REQUEST_ID, requestId);
        log.info("[Demo01BConsumer onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), record);
    }

}


