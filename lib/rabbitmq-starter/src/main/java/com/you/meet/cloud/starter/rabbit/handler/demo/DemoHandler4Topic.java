package com.you.meet.cloud.starter.rabbit.handler.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.you.meet.cloud.starter.rabbit.handler.BaseMqMsgHandler;
import com.you.meet.nice.lib.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujl
 * @date 2021/4/20 16:35
 * @desc
 */
@Slf4j
@Component
public class DemoHandler4Topic implements BaseMqMsgHandler {

    @Override
    public void handle(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        List<String> msgList = JsonUtil.parseStr2Collection(body, new TypeReference<List<String>>() {
        });
        log.info("DemoHandler4Topic消费消息{}", JsonUtil.parseObj2PrettyStr(msgList));
    }
}
