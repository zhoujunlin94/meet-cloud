package io.github.zhoujunlin94.cloud.provider.rocketmq.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.cloud.provider.rocketmq.dto.BaseMessageDTO;
import io.github.zhoujunlin94.meet.common.util.RequestIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.MDC;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2023年12月29日 9:57
 * @desc
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class SendMsgController {

    private final RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC_DEMO01 = "TOPIC_DEMO01";
    private static final String TOPIC_DEMO02 = "TOPIC_DEMO02";
    private static final String TOPIC_DEMO03 = "TOPIC_DEMO03";
    private static final String TOPIC_DEMO04 = "TOPIC_DEMO04";
    private static final String TOPIC_DEMO05 = "TOPIC_DEMO05";
    private static final String TOPIC_DEMO06 = "TOPIC_DEMO06";
    private static final String TOPIC_DEMO07 = "TOPIC_DEMO07";

    @GetMapping("/syncSend")
    public SendResult syncSend() {
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // 同步发送消息
        //rocketMQTemplate.convertAndSend();
        return rocketMQTemplate.syncSend(TOPIC_DEMO01, message);
    }

    @GetMapping("/asyncSend")
    public void asyncSend() {
        String requestId = MDC.get(RequestIdUtil.REQUEST_ID);
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // 异步发送消息
        rocketMQTemplate.asyncSend(TOPIC_DEMO01, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                log.info("[asyncSend][发送编号:{} 发送成功,结果为:{}]", message.getBizId(), sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                log.info("[asyncSend][发送编号:{} 发送成功,发送异常]", message.getBizId(), throwable);
            }
        });
    }

    @GetMapping("/onewaySend")
    public void onewaySend() {
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // OneWay发送消息  只发送请求不等待应答。  适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集
        rocketMQTemplate.sendOneWay(TOPIC_DEMO01, message);
    }

    @GetMapping("/sendBatch")
    public SendResult sendBatch() {
        List<Message<BaseMessageDTO>> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BaseMessageDTO message = new BaseMessageDTO();
            message.setBizId(IdUtil.fastSimpleUUID());
            // 构建Spring Messaging定义的Message消息
            messages.add(MessageBuilder.withPayload(message).build());
        }
        /**
         * 官⽅建议批量消息的总⼤⼩不应超过1m，实际不应超过4m。如果超过4m的批量消息需要进⾏分批处理，
         * 同时设置broker的配置参数为4m（在broker的配置⽂件中修改： maxMessageSize=4194304 ）
         */
        // 同步批量发送消息
        return rocketMQTemplate.syncSend(TOPIC_DEMO02, messages, 30 * 1000L);
    }

    @GetMapping("/syncSendDelay")
    public SendResult syncSendDelay(@RequestParam Integer delayLevel) {
        BaseMessageDTO messageDTO = new BaseMessageDTO();
        messageDTO.setBizId(IdUtil.fastSimpleUUID());
        Message<BaseMessageDTO> message = MessageBuilder.withPayload(messageDTO).build();
        // 带超时时间的延迟任务  同步发送      延迟级别3  即10s
        return rocketMQTemplate.syncSend(TOPIC_DEMO03, message, 30 * 1000, delayLevel);
    }

    @GetMapping("/asyncSendDelay")
    public void asyncSendDelay(@RequestParam int delayLevel) {
        String requestId = MDC.get(RequestIdUtil.REQUEST_ID);
        BaseMessageDTO messageDTO = new BaseMessageDTO();
        messageDTO.setBizId(IdUtil.fastSimpleUUID());
        Message<BaseMessageDTO> message = MessageBuilder.withPayload(messageDTO).build();
        // 带超时时间的延迟任务  异步发送
        rocketMQTemplate.asyncSend(TOPIC_DEMO03, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                log.info("[asyncSendDelay][发送编号:{} 发送成功,结果为:{}]", messageDTO.getBizId(), sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                log.info("[asyncSendDelay][发送编号:{} 发送成功,发送异常]", messageDTO.getBizId(), throwable);
            }
        }, 30 * 1000, delayLevel);
    }

    @GetMapping("/syncSendWithRetry")
    public SendResult syncSendWithRetry() {
        BaseMessageDTO messageDTO = new BaseMessageDTO();
        messageDTO.setBizId(IdUtil.fastSimpleUUID());
        // 同步发送消息  测试消费重试
        return rocketMQTemplate.syncSend(TOPIC_DEMO04, messageDTO);
    }

    @GetMapping("/syncSendWithBroadcast")
    public SendResult syncSendWithBroadcast() {
        BaseMessageDTO messageDTO = new BaseMessageDTO();
        messageDTO.setBizId(IdUtil.fastSimpleUUID());
        // 同步发送消息  测试广播消息
        return rocketMQTemplate.syncSend(TOPIC_DEMO05, messageDTO);
    }

    @GetMapping("/syncSendOrderly")
    public void syncSendOrderly() {
        for (int i = 0; i < 10; i++) {
            BaseMessageDTO messageDTO = new BaseMessageDTO();
            messageDTO.setBizId(IdUtil.fastSimpleUUID() + "_" + i);
            // 同步发送消息  hashKey
            rocketMQTemplate.syncSendOrderly(TOPIC_DEMO06, messageDTO, "syncSendOrderly");
        }
    }

    @GetMapping("/asyncSendOrderly")
    public void asyncSendOrderly() {
        String requestId = MDC.get(RequestIdUtil.REQUEST_ID);
        for (int i = 0; i < 10; i++) {
            BaseMessageDTO messageDTO = new BaseMessageDTO();
            messageDTO.setBizId(IdUtil.fastSimpleUUID() + "_" + i);
            // 同步发送消息  hashKey
            rocketMQTemplate.asyncSendOrderly(TOPIC_DEMO06, messageDTO, "asyncSendOrderly", new SendCallback() {

                @Override
                public void onSuccess(SendResult result) {
                    MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                    log.info("[asyncSendOrderly][发送编号：[{}] 发送成功，结果为：[{}]]", messageDTO.getBizId(), result);
                }

                @Override
                public void onException(Throwable e) {
                    MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                    log.info("[asyncSendOrderly][发送编号：[{}] 发送异常]]", messageDTO.getBizId(), e);
                }

            });
        }
    }

    @GetMapping("/sendOneWayOrderly")
    public void sendOneWayOrderly() {
        for (int i = 0; i < 10; i++) {
            BaseMessageDTO message = new BaseMessageDTO();
            message.setBizId(IdUtil.fastSimpleUUID() + "_" + i);
            // OneWay发送消息  只发送请求不等待应答。  适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集
            rocketMQTemplate.sendOneWayOrderly(TOPIC_DEMO06, message, "sendOneWayOrderly");
        }
    }

    @GetMapping("/sendMessageInTransaction")
    public void sendMessageInTransaction() {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        BaseMessageDTO messageDTO = new BaseMessageDTO();
        for (int i = 0; i < 10; i++) {
            String tag = tags[i % tags.length];
            messageDTO.setBizId(IdUtil.fastSimpleUUID() + "_" + i + "_" + tag);
            Message<BaseMessageDTO> message = MessageBuilder.withPayload(messageDTO).build();
            // topic + ":" + tag
            String destination = StrUtil.join(StrUtil.COLON, TOPIC_DEMO07, tag);
            //第一个destination为消息的目的地  第二个destination为消息携带的业务信息
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
            log.warn("{}:{}事务消息发送结果{}", i, destination, transactionSendResult);
        }
    }

}
