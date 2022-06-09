package com.you.meet.cloud.starter.redis.consumer;

import com.you.meet.nice.lib.common.constant.CommonConstant;
import com.you.meet.nice.lib.common.util.RequestIdUtil;
import com.you.meet.cloud.starter.redis.dispatcher.PublishMsgDispather;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author zhoujl
 * @date 2021/4/19 18:01
 * @desc
 */
@Slf4j
public class RedisPublishMsgConsumer implements MessageListener {

    private final PublishMsgDispather publishMsgDispather;

    public RedisPublishMsgConsumer(PublishMsgDispather publishMsgDispather) {
        this.publishMsgDispather = publishMsgDispather;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        MDC.put(CommonConstant.REQUEST_ID, RequestIdUtil.requestId());
        try {
            String channel = new String(message.getChannel());
            String msg = new String(message.getBody());
            publishMsgDispather.dispath(channel, msg);
        } catch (Exception e) {
            log.error("分发发布订阅队列消息出错", e);
        } finally {
            MDC.remove(CommonConstant.REQUEST_ID);
        }
    }
}
