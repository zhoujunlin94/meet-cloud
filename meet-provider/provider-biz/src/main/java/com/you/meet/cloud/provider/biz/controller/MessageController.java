package com.you.meet.cloud.provider.biz.controller;

import cn.hutool.core.util.RandomUtil;
import com.you.meet.cloud.provider.biz.message.MySource;
import com.you.meet.cloud.provider.biz.message.entity.Demo01Message;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
