package com.you.meet.cloud.starter.rabbit.handler.demo;

import cn.hutool.core.collection.CollUtil;
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
 * @date 2021/5/4 14:45
 * @desc
 */
@Slf4j
@Component
public class DemoHandler4Fanout implements BaseMqMsgHandler {

    @Override
    public void handle(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        List<String> msgList = JsonUtil.parseStr2Collection(body, new TypeReference<List<String>>() {
        });
        if (CollUtil.isEmpty(msgList)) {
            log.info("消费者未获取到消息");
            return;
        }
        log.info("FanoutQueueHandler消费消息{}", JsonUtil.parseObj2PrettyStr(msgList));
    }

}
