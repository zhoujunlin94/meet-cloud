//package com.you.meet.cloud.provider.biz.message.trns;
//
//import com.alibaba.fastjson.JSON;
//import com.you.meet.cloud.provider.biz.controller.MessageController;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
//import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
//import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
//import org.springframework.messaging.Message;
//
///**
// * @author zhoujunlin
// * @date 2023年02月26日 16:51
// * @desc
// */
//@Slf4j
//@RocketMQTransactionListener(txProducerGroup = "demo04-provider-group-DEMO-TOPIC-04")
//public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
//
//    @Override
//    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
//        // 从消息 Header 中解析到 args 参数，并使用 JSON 反序列化
//        MessageController.Args args = JSON.parseObject(message.getHeaders().get("args", String.class),
//                MessageController.Args.class);
//        // ... local transaction process, return rollback, commit or unknown
//        log.info("[executeLocalTransaction][执行本地事务，消息：{} args：{}]", message, args);
//        return RocketMQLocalTransactionState.UNKNOWN;
//    }
//
//    @Override
//    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
//        // ... check transaction status and return rollback, commit or unknown
//        log.info("[checkLocalTransaction][回查消息：{}]", message);
//        return RocketMQLocalTransactionState.COMMIT;
//    }
//}
