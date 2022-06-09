package com.you.meet.nice.starter.redis.handler.demo;

import com.you.meet.nice.starter.redis.handler.BasePublishMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2022/1/1
 * @desc
 **/
@Slf4j
@Component
public class PublishDemoHandler implements BasePublishMsgHandler {

    @Override
    public void handler(String channel, String msg) {
        log.info("PublishDemoHandler当前channel:{},接收到消息:{}", channel, msg);
    }
}
