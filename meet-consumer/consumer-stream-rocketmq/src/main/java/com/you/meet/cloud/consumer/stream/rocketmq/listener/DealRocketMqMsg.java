package com.you.meet.cloud.consumer.stream.rocketmq.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年12月28日 16:05
 * @desc
 */
@Component
public class DealRocketMqMsg {

    @StreamListener("input1")
    public void receiveInput1(String receiveMsg) {
        System.out.println("input1 receive: " + receiveMsg);
    }

    @StreamListener("input2")
    public void receiveInput2(String receiveMsg) {
        System.out.println("input2 receive: " + receiveMsg);
    }

}
