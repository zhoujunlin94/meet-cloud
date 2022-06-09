package com.you.meet.nice.starter.redis.pojo.queue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.starter.redis.constant.RedisConstant;
import com.you.meet.nice.starter.redis.dispatcher.QueueMsgDispather;
import com.you.meet.nice.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujl
 * @date 2021/3/5 14:51
 * @desc
 */
@Slf4j
public class RedisDelayingQueue {

    private RedisService redisService;

    public RedisDelayingQueue(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 向队列中添加消息
     *
     * @param handlerName 处理器名字
     * @param msg
     * @param delayTime   延迟执行时间 秒
     */
    public void delay(String handlerName, Object msg, int delayTime) {
        TaskItem task = new TaskItem(IdUtil.fastSimpleUUID(), handlerName, msg);
        redisService.zadd(RedisConstant.QueueMsg.QUEUE_KEY, JsonUtil.parseObj2Str(task), (System.currentTimeMillis() + (delayTime * 1000L)));
    }

    public void start(QueueMsgDispather queueMsgDispather, RedisService redisService) {
        String luaStr = RedisConstant.QueueMsg.QUEUE_LUA_STR;
        log.info("生产消费者模式lua:{}", luaStr);
        while (!Thread.interrupted()) {
            String nowStamp = System.currentTimeMillis() + "";
            List<String> msgList = redisService.executeLua(luaStr, Collections.singletonList(RedisConstant.QueueMsg.QUEUE_KEY), Collections.singletonList(nowStamp), List.class);
            if (CollUtil.isEmpty(msgList)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            msgList.forEach(msgStr -> {
                TaskItem task = JsonUtil.parseStr2Obj(msgStr, new TypeReference<TaskItem>() {
                });
                queueMsgDispather.dispath(task.getHandlerName(), task);
            });
        }
    }
}