package com.you.meet.nice.starter.redis.dispatcher;

import com.you.meet.nice.starter.redis.handler.BasePublishMsgHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujl
 * @date 2021/4/19 10:33
 * @desc
 */
@Slf4j
public class PublishMsgDispather {

    private final ConcurrentHashMap<String, BasePublishMsgHandler> publishMsgHandlerMap = new ConcurrentHashMap<>(16);

    public void register(String msgName, BasePublishMsgHandler publishMsgHandler) {
        publishMsgHandlerMap.put(msgName, publishMsgHandler);
    }

    public void dispath(String channel, String msg) {
        try {
            BasePublishMsgHandler msgHandler = publishMsgHandlerMap.get(channel);
            if (Objects.isNull(msgHandler)) {
                log.error("未找到对应通道{}处理器,消息:{}", channel, msg);
                return;
            }
            msgHandler.handler(channel, msg);
        } catch (Exception e) {
            log.error("分发消息出错", e);
        }
    }
}
