package com.you.meet.cloud.provider.message.trns;

import com.alibaba.fastjson.JSONObject;
import com.you.meet.cloud.provider.controller.MessageController;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年02月26日 16:51
 * @desc
 */
@Slf4j
@Component
public class TransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        // 从消息 Header 中解析到 args 参数，并使用 JSON 反序列化
        String args = message.getProperties().get("args");
        MessageController.Args argsObj = JSONObject.parseObject(args, MessageController.Args.class);
        // ... local transaction process, return rollback, commit or unknown
        log.info("[executeLocalTransaction][执行本地事务,消息:{},args:{}]", message, argsObj);
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.info("[checkLocalTransaction][回查消息：{}]", messageExt);
        return LocalTransactionState.COMMIT_MESSAGE;
    }

}
