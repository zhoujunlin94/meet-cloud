package com.you.meet.nice.starter.ws.dispatcher;

import com.you.meet.nice.lib.common.constant.CommonConstant;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.lib.common.util.RequestIdUtil;
import com.you.meet.nice.starter.ws.handler.BaseWSMsgHandler;
import com.you.meet.nice.starter.ws.pojo.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujl
 * @date 2021/5/16 12:15
 * @desc
 */
@Slf4j
public class WSMsgDispather {

    private final ConcurrentHashMap<String, BaseWSMsgHandler> wsMsgHandlerMap = new ConcurrentHashMap<>(16);

    public void register(String msgName, BaseWSMsgHandler baseWSMsgHandler) {
        wsMsgHandlerMap.put(msgName, baseWSMsgHandler);
    }

    public void dispath(String msgName, MessageDto messageDto) {
        MDC.put(CommonConstant.REQUEST_ID, RequestIdUtil.requestId());
        try {
            BaseWSMsgHandler msgHandler = wsMsgHandlerMap.get(msgName);
            if (Objects.isNull(msgHandler)) {
                log.error("未找到消息处理器:{}", JsonUtil.parseObj2Str(messageDto));
                return;
            }
            msgHandler.handler(messageDto);
        } catch (Exception e) {
            log.error("处理ws消息出错", e);
        } finally {
            MDC.remove(CommonConstant.REQUEST_ID);
        }
    }
}
