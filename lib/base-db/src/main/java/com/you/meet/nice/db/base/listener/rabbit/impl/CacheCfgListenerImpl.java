package com.you.meet.nice.db.base.listener.rabbit.impl;

import com.alibaba.druid.pool.GetConnectionTimeoutException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.starter.rabbit.constant.MqConstant;
import com.you.meet.nice.starter.rabbit.handler.BaseMqMsgHandler;
import com.you.meet.nice.starter.rabbit.listener.EasyCacheListener;
import com.you.meet.nice.starter.web.annotation.AsyncMdc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujl
 */
@Component
@Slf4j
public class CacheCfgListenerImpl implements EasyCacheListener {

    private final ConcurrentHashMap<String, BaseMqMsgHandler> handlers = new ConcurrentHashMap<>();

    @Override
    public void registerMessageHandler(String messageKey, BaseMqMsgHandler messageHandler) {
        handlers.put(messageKey, messageHandler);
    }

    @AsyncMdc
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(durable = "true", autoDelete = "true"),
            exchange = @Exchange(value = MqConstant.Exchange.CACHE_DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true", durable = "true"),
            key = MqConstant.RoutingKey.CACHE_ROUTING_DIRECT))
    public void refreshCache(Message message, Channel channel) throws IOException {
        String handlerName = message.getMessageProperties().getHeader(MqConstant.MQ_HANDLER_NAME);
        MqConstant.Ack action = MqConstant.Ack.RETRY;
        List<String> msgs = new ArrayList<>();
        try {
            byte[] body = message.getBody();
            //json解析
            msgs = JsonUtil.parseStr2Collection(body, new TypeReference<List<String>>() {
            });
            BaseMqMsgHandler handler = handlers.get(handlerName);
            if (Objects.nonNull(handler)) {
                handler.handle(message, channel);
            } else {
                log.error("没有对应的处理策略:{}", handlerName);
                action = MqConstant.Ack.REJECT;
            }
            action = MqConstant.Ack.ACCEPT;
        } catch (GetConnectionTimeoutException e) {
            log.error("RabbitMQ刷新内存失败，druid连接超时，重新发送消息", e);
        } catch (NoRouteToHostException e) {
            log.error("RabbitMQ刷新内存失败，连接主机失败，重新发送消息", e);
        } catch (SQLRecoverableException e) {
            log.error("RabbitMQ刷新内存失败，数据库连接失败，重新发送消息", e);
        } catch (Exception e) {
            action = MqConstant.Ack.REJECT;
            log.error("RabbitMQ刷新内存失败，程序出错，无需重新发送消息。RoutingKey={},message={}", MqConstant.RoutingKey.CACHE_ROUTING_DIRECT, msgs, e);
        } finally {
            long tag = message.getMessageProperties().getDeliveryTag();
            // 通过finally块来保证Ack/Nack会且只会执行一次
            if (action == MqConstant.Ack.ACCEPT) {
                channel.basicAck(tag, false);
                log.info("刷新成功，ack成功返回，删除队列中消息");
            } else if (action == MqConstant.Ack.RETRY) {
                channel.basicNack(tag, false, true);
            } else {
                channel.basicNack(tag, false, false);
            }
        }
    }
}