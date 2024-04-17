package io.github.zhoujunlin94.cloud.consumer.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2022年12月26日 21:35
 * @desc
 */
@Slf4j
@Component
public class Demo07Consumer {

    @KafkaListener(
            topics = {"TOPIC_DEMO07"},
            groupId = "TOPIC_DEMO07-consumer-group"
    )
    public void onMessage(String messages, Acknowledgment acknowledgment) {
        log.info("[Demo07Consumer.onMessage][线程编号:{}, 消息内容:{}]", Thread.currentThread().getId(), messages);
        JSONObject messageJson = JSONObject.parseObject(messages);
        Integer bizId = messageJson.getInteger("bizId");
        // 奇数时提交消费进度
        if (bizId % 2 == 1) {
            acknowledgment.acknowledge();
        }
    }

}


