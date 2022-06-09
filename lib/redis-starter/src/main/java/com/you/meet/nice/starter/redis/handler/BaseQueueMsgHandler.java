package com.you.meet.nice.starter.redis.handler;

import com.you.meet.nice.starter.redis.pojo.queue.TaskItem;

/**
 * @author zhoujl
 * @date 2021/4/19 10:35
 * @desc
 */
public interface BaseQueueMsgHandler {

    /**
     * 处理队列中的消息
     *
     * @param taskItem
     */
    void handler(TaskItem taskItem);

}
