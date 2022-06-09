package com.you.meet.cloud.starter.redis.handler;

/**
 * @author zhoujl
 * @date 2021/4/19 10:35
 * @desc
 */
public interface BasePublishMsgHandler {

    /**
     * 处理发布订阅模式的消息
     *
     * @param channel 通道
     * @param msg     消息
     */
    void handler(String channel, String msg);

}
