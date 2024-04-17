package io.github.zhoujunlin94.cloud.provider.kafka.controller;

import cn.hutool.core.util.IdUtil;
import io.github.zhoujunlin94.cloud.provider.kafka.dto.BaseMessageDTO;
import io.github.zhoujunlin94.meet.common.util.RequestIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author zhoujunlin
 * @date 2023年12月29日 14:57
 * @desc
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class SendMsgController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_DEMO01 = "TOPIC_DEMO01";
    private static final String TOPIC_DEMO02 = "TOPIC_DEMO02";
    private static final String TOPIC_DEMO03 = "TOPIC_DEMO03";
    private static final String TOPIC_DEMO04 = "TOPIC_DEMO04";
    private static final String TOPIC_DEMO05 = "TOPIC_DEMO05";
    private static final String TOPIC_DEMO06 = "TOPIC_DEMO06";
    private static final String TOPIC_DEMO07 = "TOPIC_DEMO07";

    @GetMapping("/syncSend")
    public SendResult<String, String> syncSend() throws ExecutionException, InterruptedException {
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // 同步发送消息
        return kafkaTemplate.send(TOPIC_DEMO01, message.toString()).get();
    }

    @GetMapping("/asyncSend")
    public void asyncSend() {
        String requestId = MDC.get(RequestIdUtil.REQUEST_ID);
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // 异步发送消息
        kafkaTemplate.send(TOPIC_DEMO01, message.toString()).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onFailure(Throwable e) {
                MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                log.info("[asyncSend.onFailure][发送编号：[{}] 发送异常]]", message.getBizId(), e);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                log.info("[asyncSend.onSuccess][发送编号：[{}] 发送成功，结果为：[{}]]", message.getBizId(), result);
            }

        });
    }

    @GetMapping("/asyncSendBatch")
    public void asyncSendBatch() throws InterruptedException {
        // todo 需要开启生产者批量发送相关配置
        String requestId = MDC.get(RequestIdUtil.REQUEST_ID);
        for (int i = 0; i < 3; i++) {
            BaseMessageDTO message = new BaseMessageDTO();
            message.setBizId(IdUtil.fastSimpleUUID() + "_" + i);
            kafkaTemplate.send(TOPIC_DEMO02, message.toString()).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                @Override
                public void onFailure(Throwable e) {
                    MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                    log.info("[asyncSendWithLingerMs.onFailure][发送编号：[{}] 发送异常]]", message.getBizId(), e);
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    MDC.put(RequestIdUtil.REQUEST_ID, requestId);
                    // 由于是批量发送  所以要等到30s一起发送之后才会回调
                    log.info("[asyncSendWithLingerMs.onSuccess][发送编号：[{}] 发送成功，结果为：[{}]]", message.getBizId(), result);
                }

            });

            // 故意每条消息停顿10秒  达到linger.ms条件
            Thread.sleep(10 * 1000L);
        }
    }

    @GetMapping("/syncSendAndRetry")
    public SendResult syncSendAndRetry() throws ExecutionException, InterruptedException {
        // 重试机制：kafkaErrorHandler
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // 同步发送消息
        return kafkaTemplate.send(TOPIC_DEMO03, message.toString()).get();
    }

    @GetMapping("/syncSendAndBroadcast")
    public SendResult syncSendAndBroadcast() throws ExecutionException, InterruptedException {
        BaseMessageDTO message = new BaseMessageDTO();
        message.setBizId(IdUtil.fastSimpleUUID());
        // 同步发送消息
        return kafkaTemplate.send(TOPIC_DEMO04, message.toString()).get();
    }

    @GetMapping("/syncSendNotOrderly")
    public void syncSendNotOrderly() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            BaseMessageDTO message = new BaseMessageDTO();
            message.setBizId(IdUtil.fastSimpleUUID() + "_" + i);
            kafkaTemplate.send(TOPIC_DEMO05, message.toString()).get();
        }
    }

    @GetMapping("/syncSendOrderly")
    public void syncSendOrderly() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            BaseMessageDTO message = new BaseMessageDTO();
            message.setBizId(IdUtil.fastSimpleUUID() + "_" + i);
            // 同步发送消息
            // key的序列化方式是String 所以传key的类型也必须是String  相同的key发送到相同的partition
            kafkaTemplate.send(TOPIC_DEMO05, "syncSendOrderly", message.toString()).get();
        }
    }

    @GetMapping("/syncSendInTransaction")
    public String syncSendInTransaction() throws ExecutionException, InterruptedException {
        Runnable runnable = () -> {
            log.info("[run][我要开始睡觉了]");
            try {
                // 事务提交后才会消费
                Thread.sleep(10 * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("[run][我睡醒了]");
        };

        return kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<String, String, String>() {
            @Override
            public String doInOperations(KafkaOperations<String, String> kafkaOperations) {
                BaseMessageDTO messageDTO = new BaseMessageDTO();
                messageDTO.setBizId(IdUtil.fastSimpleUUID());
                try {
                    SendResult<String, String> sendResult = kafkaOperations.send(TOPIC_DEMO06, messageDTO.toString()).get();
                    log.info("[syncSendInTransaction.doInOperations][发送编号：[{}] 发送结果：[{}]]", messageDTO.getBizId(), sendResult);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                // 本地业务逻辑... biubiubiu   模拟本地事务  当这个方法执行完成后再success
                runnable.run();
                // 返回结果
                return "success";
            }
        });
    }

    @GetMapping("/syncSendAndAck")
    public void syncSendAndAck() throws ExecutionException, InterruptedException {
        for (int i = 0; i <= 10; i++) {
            BaseMessageDTO messageDTO = new BaseMessageDTO();
            messageDTO.setBizId(String.valueOf(i));
            // 异步发送消息
            kafkaTemplate.send(TOPIC_DEMO07, messageDTO.toString()).get();
        }
    }

}
