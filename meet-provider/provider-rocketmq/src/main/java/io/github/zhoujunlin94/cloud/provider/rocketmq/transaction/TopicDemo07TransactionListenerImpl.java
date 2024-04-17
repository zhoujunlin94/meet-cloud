package io.github.zhoujunlin94.cloud.provider.rocketmq.transaction;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

/**
 * @author zhoujunlin
 * @date 2023年12月29日 10:30
 * @desc
 */
@Slf4j
@RocketMQTransactionListener
public class TopicDemo07TransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // RocketMQTemplate 已经使用 Producer 发送了一条事务消息。然后根据该方法执行的返回的 RocketMQLocalTransactionState 结果，
        // 提交还是回滚该事务消息。  提交：消费者可以消费  回滚：消费者不可以消费
        log.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", msg, arg);
        String destination = (String) arg;
        org.apache.rocketmq.common.message.Message rocketMessage = RocketMQUtil.convertToRocketMessage(new StringMessageConverter(), "utf-8", destination, msg);
        String tags = rocketMessage.getTags();
        if (StrUtil.containsIgnoreCase(tags, "TagA")) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StrUtil.containsIgnoreCase(tags, "TagB")) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 在事务消息长时间未被提交或回滚时(UNKNOWN)，RocketMQ 会回查事务消息对应的生产者分组（rocketmq.producer.group）下的Producer ，获得事务消息的状态。此时，该方法就会被调用。
        // 如果回查过后还是UNKNOWN  继续回查  直到15次之后丢弃
        log.info("[checkLocalTransaction][回查消息：{}]", msg);
        return RocketMQLocalTransactionState.COMMIT;
    }

}
