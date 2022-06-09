package com.you.meet.nice.starter.redis.dispatcher;

import com.you.meet.nice.lib.common.constant.CommonConstant;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.lib.common.util.RequestIdUtil;
import com.you.meet.nice.starter.redis.handler.BaseQueueMsgHandler;
import com.you.meet.nice.starter.redis.pojo.queue.TaskItem;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujl
 * @date 2021/4/19 10:33
 * @desc
 */
@Slf4j
public class QueueMsgDispather {

    private final ConcurrentHashMap<String, BaseQueueMsgHandler> queueMsgHandlerMap = new ConcurrentHashMap<>(16);

    public void register(String msgName, BaseQueueMsgHandler baseQueueMsgHandler) {
        queueMsgHandlerMap.put(msgName, baseQueueMsgHandler);
    }

    public void dispath(String msgName, TaskItem taskItem) {
        MDC.put(CommonConstant.REQUEST_ID, RequestIdUtil.requestId());
        try {
            BaseQueueMsgHandler msgHandler = queueMsgHandlerMap.get(msgName);
            if (Objects.isNull(msgHandler)) {
                log.error("未找到消息处理器:{}", JsonUtil.parseObj2Str(taskItem));
                return;
            }
            msgHandler.handler(taskItem);
        } catch (Exception e) {
            log.error("分发队列消息出错", e);
        } finally {
            MDC.remove(CommonConstant.REQUEST_ID);
        }
    }
}
