package com.you.meet.cloud.provider.message;

import com.you.meet.cloud.provider.message.constant.RocketMQConstant;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 14:11
 * @desc
 */
public interface MySource {

    @Output(RocketMQConstant.DEMO01_OUTPUT_BINDING)
    MessageChannel demo01Output();

    @Output(RocketMQConstant.DEMO03_OUTPUT_BINDING)
    MessageChannel demo03Output();

    @Output(RocketMQConstant.DEMO04_OUTPUT_BINDING)
    MessageChannel demo04Output();

}
