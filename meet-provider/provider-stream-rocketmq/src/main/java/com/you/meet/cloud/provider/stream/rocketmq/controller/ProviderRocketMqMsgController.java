package com.you.meet.cloud.provider.stream.rocketmq.controller;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年12月28日 15:40
 * @desc
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/provider")
public class ProviderRocketMqMsgController {

    private final MessageChannel output1;

    @GetMapping("/send")
    public void sendMsg(@RequestParam String msg, @RequestParam String tag) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, tag);
        Message<String> message = MessageBuilder.createMessage(msg, new MessageHeaders(headers));
        output1.send(message);
    }

}
