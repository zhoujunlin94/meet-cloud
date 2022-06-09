package com.you.meet.nice.starter.rabbit.action;

import java.util.List;

/**
 * @author zhoujl
 * @date 2021/4/20 14:05
 * @desc
 */
public interface MqMsgSendAction {

    /**
     * 发送mq消息
     *
     * @param exchange          交换机名
     * @param routingKey        routingkey
     * @param registHandlerName 注册的处理类对应名
     * @param msg               具体消息
     */
    <T> void sendMsg(String exchange, String routingKey, String registHandlerName, T msg);

    /**
     * 发送mq消息
     *
     * @param exchange
     * @param routingKey
     * @param registHandlerName
     * @param msg
     * @param <T>
     */
    <T> void sendMsg(String exchange, String routingKey, String registHandlerName, List<T> msg);
}
