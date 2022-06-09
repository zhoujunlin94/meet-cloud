package com.you.meet.cloud.starter.redis.constant;

/**
 * @author zhoujl
 * @date 2022/3/12 16:35
 * @desc
 */
public final class LuaScripts {

    public static final String LUA_SCRIPTS = "local c = redis.call('get',KEYS[1]) " +
            "if c and tonumber(c) > tonumber(ARGV[1]) then " +
            "return c; " +
            "end " +
            "c = redis.call('incr',KEYS[1]) " +
            "if tonumber(c) == 1 then " +
            "redis.call('expire',KEYS[1],ARGV[2]) " +
            "end " +
            "return c;";
}
