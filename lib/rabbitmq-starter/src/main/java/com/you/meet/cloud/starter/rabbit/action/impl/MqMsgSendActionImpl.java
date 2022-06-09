package com.you.meet.cloud.starter.rabbit.action.impl;

import cn.hutool.core.util.IdUtil;
import com.you.meet.cloud.starter.rabbit.action.MqMsgSendAction;
import com.you.meet.cloud.starter.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhoujl
 * @date 2021/4/20 14:05
 * @desc
 */
@Slf4j
@Service
public class MqMsgSendActionImpl implements MqMsgSendAction {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public <T> void sendMsg(String exchange, String routingKey, String registHandlerName, T msg) {
        CorrelationData correlationData = new CorrelationData(IdUtil.simpleUUID());
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setHeader(MqConstant.MQ_HANDLER_NAME, registHandlerName);
            return message;
        }, correlationData);
    }

    @Override
    public <T> void sendMsg(String exchange, String routingKey, String registHandlerName, List<T> msg) {
        CorrelationData correlationData = new CorrelationData(IdUtil.simpleUUID());
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setHeader(MqConstant.MQ_HANDLER_NAME, registHandlerName);
            return message;
        }, correlationData);
    }
}
