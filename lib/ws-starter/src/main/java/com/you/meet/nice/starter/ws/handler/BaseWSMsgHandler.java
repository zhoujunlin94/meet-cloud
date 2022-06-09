package com.you.meet.nice.starter.ws.handler;

import com.you.meet.nice.starter.ws.pojo.MessageDto;

/**
 * @author zhoujl
 * @date 2021/5/16 12:15
 * @desc
 */
public interface BaseWSMsgHandler {

    /**
     * 处理ws消息
     *
     * @param messageDto
     */
    void handler(MessageDto messageDto);

}
