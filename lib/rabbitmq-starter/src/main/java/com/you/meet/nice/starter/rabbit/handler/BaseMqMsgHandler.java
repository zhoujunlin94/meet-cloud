package com.you.meet.nice.starter.rabbit.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author zhoujl
 * @date 2021/4/20 14:46
 * @desc
 */
public interface BaseMqMsgHandler {

    void handle(Message message, Channel channel) throws Exception;

}
