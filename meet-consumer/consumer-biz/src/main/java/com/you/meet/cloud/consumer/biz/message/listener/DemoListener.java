package com.you.meet.cloud.consumer.biz.message.listener;

import com.you.meet.cloud.consumer.biz.message.constant.RocketMQConstant;
import com.you.meet.cloud.consumer.biz.message.entity.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
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
    public void onMessage1(@Payload Demo01Message message) {
        log.info("[onMessage1][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
        //throw new RuntimeException("我就是故意抛出一个异常");
    }

    //@StreamListener(RocketMQConstant.DEMO02_INPUT_BINDING)
    public void onMessage2(@Payload Demo01Message message) {
        // 广播消息
        log.info("[onMessage2][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

    /**
     * 局部异常处理
     * topic.group.errors
     *
     * @param errorMessage
     */
    //@ServiceActivator(inputChannel = "DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.errors")
    public void handleError(ErrorMessage errorMessage) {
        log.error("[handleError][payload：{}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.error("[handleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[handleError][headers：{}]", errorMessage.getHeaders());
    }

    /**
     * 全局异常处理
     *
     * @param errorMessage
     */
    //@StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME) // errorChannel
    public void globalHandleError(ErrorMessage errorMessage) {
        log.error("[globalHandleError][payload：{}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());
    }

}
