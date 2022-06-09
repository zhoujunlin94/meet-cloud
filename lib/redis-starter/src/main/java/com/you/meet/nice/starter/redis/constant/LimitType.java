package com.you.meet.nice.starter.redis.constant;

/**
 * @Author zhoujl
 * @Date 2020/5/4 12:06
 * @Description 限流类型
 **/
public enum LimitType {

    /**
     * 传统类型
     */
    NORMAL,

    /**
     * 根据 IP地址限制
     */
    IP
}
