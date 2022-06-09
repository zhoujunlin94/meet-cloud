package com.you.meet.cloud.starter.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.you.meet.cloud.starter.rabbit.constant.MqConstant;
import com.you.meet.cloud.starter.rabbit.handler.BaseMqMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @author zhoujl
 * @date 2021/4/20 14:48
 * @desc
 */
@Slf4j
public class BaseConsumer {

    protected void consumer(Message message, Channel channel, BaseMqMsgHandler msgHandler, String logName,
                            String queueName, String routingKey) throws IOException {
        log.info("RabbitMq消费并处理{}开始,队列名{},routingKey:{}", logName, queueName, routingKey);
        int batchCount = 1;
        channel.basicQos(batchCount);
        MqConstant.Ack ack = MqConstant.Ack.RETRY;
        try {
            if (msgHandler != null) {
                msgHandler.handle(message, channel);
                ack = MqConstant.Ack.ACCEPT;
            }
        } catch (Exception e) {
            //catch异常根据实际情况判断reject和retry
            ack = MqConstant.Ack.REJECT;
            log.info("RabbitMq消费处理" + logName + "程序出错,无需重新将消息放入队列中", e);
        } finally {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            if (ack == MqConstant.Ack.ACCEPT) {
                channel.basicAck(deliveryTag, false);
                log.info("RabbitMq消费处理{}成功,ack签收,删除队列消息", logName);
            } else if (ack == MqConstant.Ack.RETRY) {
                channel.basicNack(deliveryTag, false, true);
                log.error("RabbitMq消费处理{}失败,ack重试,消息重新放入队列", logName);
            } else {
                channel.basicNack(deliveryTag, false, false);
                log.error("RabbitMq消费处理{}失败,ack拒绝,消息删除", logName);
            }
            log.info("RabbitMq消费并处理{}结束,队列名{},routingKey:{}", logName, queueName, routingKey);
        }
    }
}
