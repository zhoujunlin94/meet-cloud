package com.you.meet.nice.db.base.handler.redis;

import com.you.meet.nice.db.base.service.CacheCfgService;
import com.you.meet.nice.starter.redis.handler.BasePublishMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author zhoujl
 * @date 2022/3/18 20:46
 * @desc
 */
@Slf4j
@Component
public class CacheCfgPublishHandler implements BasePublishMsgHandler {

    @Autowired
    private CacheCfgService cacheCfgService;

    @Override
    public void handler(String channel, String msg) {
        log.info("PublishDemoHandler当前channel:{},接收到消息:{}", channel, msg);
        cacheCfgService.refreshPart(Collections.singletonList(msg));
    }
}
