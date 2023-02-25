package com.you.meet.cloud.provider.biz.message;

import com.you.meet.cloud.provider.biz.message.constant.RocketMQConstant;
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

}
