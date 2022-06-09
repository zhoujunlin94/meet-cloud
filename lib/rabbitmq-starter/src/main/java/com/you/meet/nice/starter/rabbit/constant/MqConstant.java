package com.you.meet.nice.starter.rabbit.constant;

import lombok.AllArgsConstructor;

/**
 * @author zhoujl
 * @date 2021/4/20 14:18
 * @desc
 */
public class MqConstant {

    public static final String MQ_HANDLER_NAME = "handler";
    /**
     * 刷新单条记录标识
     */
    public static final String SINGLE_REFRESH = "1";

    /**
     * 刷新整张表记录标识
     */
    public static final String ALL_REFRESH = "2";

    @AllArgsConstructor
    public enum Ack {
        /**
         * ack标识
         */
        ACCEPT("消费成功 签收"),
        REJECT("消费失败 拒收"),
        RETRY("消费失败 重试"),
        ;
        private final String desc;
    }

    /**
     * 交换机
     */
    public static class Exchange {

        public static final String DEMO_FANOUT_EXCHANGE = "demo.fanout.exchange";
        public static final String DEMO_DIRECT_EXCHANGE = "demo.direct.exchange";
        public static final String DEMO_DLX_EXCHANGE = "demo.dlx.exchange";
        public static final String DEMO_TOPIC_EXCHANGE = "demo.topic.exchange";

        /**
         * 缓存配置表交换机cache_cfg
         */
        public static final String CACHE_DIRECT_EXCHANGE = "cache.direct.exchange";

    }

    /**
     * 队列
     */
    public static class Queue {
        public static final String DEMO_QUEUE_FANOUT = "demoQueue4DemoFanout";
        public static final String DEMO_QUEUE_DIRECT = "demoQueue4DemoDirect";
        public static final String DEMO_QUEUE_DLX = "demoQueue4DemoDlx";
        public static final String DEMO_QUEUE_TOPIC = "demoQueue4DemoTopic";


        public static final String RESET_CACHE_QUEUE = "resetCacheCfgQueue";
    }

    public static class RoutingKey {
        public static final String DEMO_ROUTING_FANOUT = "demoRouting4Fanout";
        public static final String DEMO_ROUTING_DIRECT = "demoRouting4Direct";
        public static final String DEMO_ROUTING_DLX = "demoRouting4Dlx";
        public static final String DEMO_ROUTING_TOPIC = "abc.topic.*";
        /**
         * cache_cfg
         */
        public static final String CACHE_ROUTING_DIRECT = "cacheRouting4Direct";
    }

    public static class HandlerName {

        public static final String DEMO_HANDLER_FANOUT = "demoHandler4Fanout";
        public static final String DEMO_HANDLER_DIRECT = "demoHandler4Direct";
        public static final String DEMO_HANDLER_TOPIC = "demoHandler4Topic";

        public static final String CACHE_HANDLER_DIRECT = "cacheHandler4Direct";
    }
}
