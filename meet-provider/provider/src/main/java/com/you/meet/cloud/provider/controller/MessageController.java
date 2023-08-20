package com.you.meet.cloud.provider.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.you.meet.cloud.provider.message.MySource;
import com.you.meet.cloud.provider.message.entity.Demo01Message;
import lombok.Data;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 13:28
 * @desc
 */
@RequestMapping("/msg")
@RestController
public class MessageController {

    @Resource
    private MySource mySource;


    @GetMapping("/send")
    private boolean send() {
        Demo01Message demo01Message = new Demo01Message().setId(RandomUtil.randomInt());
        Message<Demo01Message> demo01SpringMsg = MessageBuilder.withPayload(demo01Message).build();
        return mySource.demo01Output().send(demo01SpringMsg);
    }

    @GetMapping("/sendDelay")
    private boolean sendDelay() {
        Demo01Message demo01Message = new Demo01Message().setId(RandomUtil.randomInt());
        Message<Demo01Message> demo01SpringMsg = MessageBuilder.withPayload(demo01Message)
                // 延迟级别3  10s后消费
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, "3").build();
        return mySource.demo01Output().send(demo01SpringMsg);
    }

    @GetMapping("/sendOrderly")
    private boolean sendOrderly() {
        Demo01Message demo01Message = new Demo01Message().setId(RandomUtil.randomInt());
        Message<Demo01Message> demo01SpringMsg = MessageBuilder.withPayload(demo01Message).build();
        for (int i = 0; i < 3; i++) {
            mySource.demo03Output().send(demo01SpringMsg);
        }
        return true;
    }

    @GetMapping("/sendTag")
    private boolean sendTag() {
        for (String tag : new String[]{"normal", "orderly", "con"}) {
            // 创建 Message
            Demo01Message message = new Demo01Message()
                    .setId(new Random().nextInt()).setTag(tag);
            // 创建 Spring Message 对象
            Message<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                    .setHeader(MessageConst.PROPERTY_TAGS, tag) // <X> 设置 Tag
                    .build();
            // 发送消息
            mySource.demo01Output().send(springMessage);
        }
        return true;
    }


    @GetMapping("/sendTransaction")
    public boolean sendTransaction() {
        // 创建 Message
        Demo01Message message = new Demo01Message()
                .setId(new Random().nextInt());
        // 创建 Spring Message 对象
        Args args = new Args().setArgs1(1).setArgs2("2");
        Message<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                .setHeader("args", JSONObject.toJSONString(args))
                .build();
        // 发送消息
        return mySource.demo04Output().send(springMessage);
    }

    @Data
    public static class Args {

        private Integer args1;
        private String args2;
    }

}
