package io.github.zhoujunlin94.cloud.consumer.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2022年12月26日 21:35
 * @desc 在前面的示例中，我们配置的每一个 Spring-Kafka @KafkaListener ，都是串行消费的。显然，这在监听的 Topic 每秒消息量比较大的时候，会导致消费不及时，导致消息积压的问题。
 * 虽然说，我们可以通过启动多个 JVM 进程，实现多进程的并发消费，从而加速消费的速度。但是问题是，是否能够实现多线程的并发消费呢？答案是有。
 * 例如说，如果设置 concurrency=4 时，Spring-Kafka 就会为该 @KafkaListener 创建 4 个线程，进行并发消费。
 * <p>
 * 首先，我们来创建一个 Topic 为 "DEMO_06" ，并且设置其 Partition 分区数为 10 。命令如下
 * kafka-topics.sh --create --zookeeper 192.168.56.10:2181 --replication-factor 1 --partitions 10 --topic DEMO_06
 * 然后，我们创建一个 Demo06Consumer 类，并在其消费方法上，添加 @KafkaListener(concurrency=2) 注解。
 * 再然后，我们启动项目。Spring-Kafka 会根据 @KafkaListener(concurrency=2) 注解，创建 2 个 Kafka Consumer 。注意噢，是 2 个 Kafka Consumer 呢！！！后续，每个 Kafka Consumer 会被单独分配到一个线程中，进行拉取消息，消费消息。
 * 之后，Kafka Broker 会将 Topic 为 "DEMO_06" 分配给创建的 2 个 Kafka Consumer 各 5 个 Partition 。
 * 这样，因为 @KafkaListener(concurrency=2) 注解，创建 2 个 Kafka Consumer ，就在各自的线程中，拉取各自的 Topic 为 "DEMO_06" 的 Partition 的消息，各自串行消费。从而，实现多线程的并发消费。
 * <p>
 * 有一点要注意，不要配置 concurrency 属性过大，则创建的 Kafka Consumer 分配不到消费 Topic 的 Partition 分区，导致不断的空轮询。
 * <p>
 * <p>
 * <p>
 * 在理解 Spring-Kafka 提供的并发消费机制，花费了好几个小时，主要陷入到了一个误区。
 * 如果胖友有使用过 RocketMQ 的并发消费，会发现只要创建一个 RocketMQ Consumer 对象，然后 Consumer 拉取完消息之后，丢到 Consumer 的线程池中执行消费，从而实现并发消费。
 * 而在 Spring-Kafka 提供的并发消费，会发现需要创建多个 Kafka Consumer 对象，并且每个 Consumer 都单独分配一个线程，然后 Consumer 拉取完消息之后，在各自的线程中执行消费。
 * 所以下面这里例子配置了2 的意思是 由这个JVM实例中，有两个消费者，也即同一个分区里的消息一定顺序消费。 因为一个消费者组里的消费者只会消费一个分区
 * 而RocketMQ是线程池，如果配置多个的话，一个消费者组里的消费者因为使用线程池，那么就无法保证消息顺序消费  因为线程执行速度先后不确定  所以有个 consumeMode = ConsumeMode.ORDERLY
 */
@Slf4j
@Component
public class Demo05Consumer {

    @KafkaListener(
            topics = {"TOPIC_DEMO05"},
            groupId = "TOPIC_DEMO05-consumer-group",
            // 每台机器开两个线程
            concurrency = "2"
    )
    public void onMessage(String message) {
        log.info("[Demo05Consumer.onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}


