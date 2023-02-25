package com.you.meet.cloud.consumer.biz.message.listener;

import com.you.meet.cloud.consumer.biz.message.constant.RocketMQConstant;
import com.you.meet.cloud.consumer.biz.message.entity.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 14:19
 * @desc
 */
@Slf4j
@Component
public class DemoListener {

    @StreamListener(RocketMQConstant.DEMO01_INPUT_BINDING)
    public void onMessage(@Payload Demo01Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
