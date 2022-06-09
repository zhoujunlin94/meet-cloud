package com.you.meet.nice.starter.rabbit.listener;

import com.you.meet.nice.starter.rabbit.handler.BaseMqMsgHandler;

/**
 * @author: zhoujl
 */
public interface EasyCacheListener {

    /**
     * 注册messageHandler
     *
     * @param msgKey         消息类型
     * @param messageHandler 对应词消息的处理函数
     */
    void registerMessageHandler(String msgKey, BaseMqMsgHandler messageHandler);

}
