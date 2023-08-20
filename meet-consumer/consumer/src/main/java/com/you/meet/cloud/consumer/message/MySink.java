package com.you.meet.cloud.consumer.message;

import com.you.meet.cloud.consumer.message.constant.RocketMQConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 14:15
 * @desc
 */
public interface MySink {

    @Input(RocketMQConstant.DEMO01_INPUT_BINDING)
    SubscribableChannel demo01Input();

    @Input(RocketMQConstant.DEMO02_INPUT_BINDING)
    SubscribableChannel demo02Input();

    @Input(RocketMQConstant.DEMO03_INPUT_BINDING)
    SubscribableChannel demo03Input();

    @Input(RocketMQConstant.DEMO04_INPUT_BINDING)
    SubscribableChannel demo04Input();
}
