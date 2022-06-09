package com.you.meet.nice.starter.redis.constant;

/**
 * @author zhoujl
 * @date 2022/3/12 21:26
 * @desc
 */
public final class RedisConstant {

    /**
     * redis生产者消费者模式常量类
     */
    public static class QueueMsg {

        /**
         * 获取队列消息lua
         */
        public static final String QUEUE_LUA_STR = " local result = redis.call('zrangebyscore', KEYS[1], 0, ARGV[1], 'LIMIT', 0, 1) \n" +
                " if next(result) ~= nil and #result > 0 then \n" +
                "    local re = redis.call('zrem', KEYS[1], unpack(result)); \n" +
                "    if re > 0 then \n" +
                "        return result; \n" +
                "    end \n" +
                "    return {} \n" +
                " else \n" +
                "    return {} \n" +
                " end \n";

        /**
         * 队列rediskey
         */
        public static final String QUEUE_KEY = "queue:msg";

        /**
         * demo消息
         */
        public static final String DEMO_MSG_NAME = "demo";
        public static final String RESET_CACHE_MSG_NAME = "resetCacheCfg";

    }

    /**
     * redis发布订阅常量类
     */
    public static class PublishRedis {
        /**
         * redis通道
         */
        public static final String PUBLISH_CHANNEL_1 = "channel:1";

        public static final String PUBLISH_CHANNEL_CACHE_CFG = "channel:cache-cfg";

        /**
         * ws 广播broadcast
         */
        public static final String PUBLISH_CHANNEL_WS_BROADCAST = "channel:WS:BROADCAST";
    }


}
