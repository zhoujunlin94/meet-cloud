package com.you.meet.cloud.provider.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author zhoujunlin
 * @date 2022年12月27日 11:22
 * @desc
 */
@Configuration
public class KafkaConfiguration {

    @Bean
    @Primary
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        // 消费重试  达到重试次数后 进入死信队列
        DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        /**
         * 在重试小于最大次数时，重新投递该消息给 Consumer ，让 Consumer 有机会重新消费消息，实现消费成功
         * 在重试到达最大次数时，Consumer 还是消费失败时，该消息就会发送到死信队列。
         * 例如说，测试的 Topic 是 "DEMO_04" ，则其对应的死信队列的 Topic 就是 "DEMO_04.DLT" ，
         * 即在原有 Topic 加上 .DLT 后缀，就是其死信队列的 Topic 。
         */
        // 重试 3 次，每次固定间隔 10 秒
        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
        /**
         * 在消息消费失败时，SeekToCurrentErrorHandler 会将 调用 Kafka Consumer 的 #seek(TopicPartition partition, long offset) 方法，
         * 将 Consumer 对于该消息对应的 TopicPartition 分区的本地进度设置成该消息的位置。这样，Consumer 在下次从 Kafka Broker 拉取消息的时候，
         * 又能重新拉取到这条消费失败的消息，并且是第一条。
         * 使用 FailedRecordTracker 对每个 Topic 的每个 TopicPartition 消费失败次数进行计数，这样相当于对该 TopicPartition
         * 的第一条消费失败的消息的消费失败次数进行计数.在 FailedRecordTracker 中，会调用 BackOff 来进行计算，该消息的下一次重新消费的时间，
         * 通过 Thread#sleep(...) 方法，实现重新消费的时间间隔。
         * FailedRecordTracker 提供的计数是客户端级别的，重启 JVM 应用后，计数是会丢失的。所以，如果想要计数进行持久化，
         * 需要自己重新实现下 FailedRecordTracker 类，通过 ZooKeeper 存储计数。
         */
        return new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer, backOff);
    }


    /*@Bean
    public BatchErrorHandler kafkaBatchErrorHandler() {
        // 创建 SeekToCurrentBatchErrorHandler 对象
        SeekToCurrentBatchErrorHandler batchErrorHandler = new SeekToCurrentBatchErrorHandler();
        // 创建 FixedBackOff 对象
        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
        batchErrorHandler.setBackOff(backOff);
        // 返回 SeekToCurrentBatchErrorHandler 暂时不支持死信队列的机制。
        return batchErrorHandler;
    }*/

}
