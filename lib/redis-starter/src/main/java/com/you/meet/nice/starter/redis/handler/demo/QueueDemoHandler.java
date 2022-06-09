package com.you.meet.nice.starter.redis.handler.demo;

import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.starter.redis.handler.BaseQueueMsgHandler;
import com.you.meet.nice.starter.redis.pojo.queue.TaskItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhoujl
 * @date 2021/4/19 17:31
 * @desc
 */
@Slf4j
@Component
public class QueueDemoHandler implements BaseQueueMsgHandler {

    @Override
    public void handler(TaskItem taskItem) {

        log.info("QueueDemoHandler接收到信息{}", JsonUtil.parseObj2Str(taskItem));

    }
}
