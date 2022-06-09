package com.you.meet.nice.starter.ws.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoujl
 * @date 2021/5/14 10:52
 * @desc
 */
public class WSConstant {

    @AllArgsConstructor
    @Getter
    public enum MsgType {
        /**
         * 全部发送 广播消息
         */
        ALL("all"),

        TEST_01("test"),

        ;
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum RedisKey {
        /**
         * ws用户连接 信息存入redis
         */
        WS_USER("ws:user:", "ws用户信息"),
        WS_MSG("ws:msg", "ws消息"),

        ;

        private final String key;
        private final String desc;

        @Override
        public final String toString() {
            return this.key;
        }
    }
}
