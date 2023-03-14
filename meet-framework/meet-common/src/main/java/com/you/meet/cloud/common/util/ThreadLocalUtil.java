package com.you.meet.cloud.common.util;

import com.alibaba.fastjson.JSONObject;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年03月14日 17:28
 * @desc
 */
public final class ThreadLocalUtil {

    private final static ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    public static Object get() {
        return THREAD_LOCAL.get();
    }

    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    public static void put(String key, Object value) {
        Object threadValue = get();
        if (Objects.isNull(threadValue)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, value);
            set(jsonObject);
        } else {
            if (threadValue instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) threadValue;
                jsonObject.put(key, value);
                set(jsonObject);
            }
        }
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}

