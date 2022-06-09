package com.you.meet.cloud.starter.rabbit.service;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.you.meet.cloud.starter.rabbit.handler.BaseMqMsgHandler;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.lib.common.util.SpringUtil;
import com.you.meet.cloud.starter.rabbit.constant.MqConstant;
import com.you.meet.cloud.starter.rabbit.listener.EasyCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujl
 * 缓存服务
 */
public interface EasyCacheService extends BaseMqMsgHandler, InitializingBean {

    Logger logger = LoggerFactory.getLogger(EasyCacheService.class);

    @Override
    default void handle(Message message, Channel channel) throws Exception {
        try {
            byte[] body = message.getBody();
            List<String> msgs = JsonUtil.parseStr2Collection(body, new TypeReference<List<String>>() {
            });
            if (MqConstant.ALL_REFRESH.equals(msgs.get(0))) {
                logger.info("CacheService 刷新全表记录缓存");
                refreshAll();
            } else if (MqConstant.SINGLE_REFRESH.equals(msgs.get(0))) {
                String msgId = msgs.get(1);
                if (StrUtil.isNotBlank(msgId)) {
                    List<String> ids = Arrays.asList(msgId.split(","));
                    logger.info("CacheService 刷新部分主键数据缓存");
                    refreshPart(ids);
                }
            } else {
                logger.error("由于消息有误,没有刷新缓存, {}", msgs);
            }
        } catch (Exception e) {
            logger.error("刷新缓存失败", e);
            throw e;
        }
    }

    @Override
    default void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            while (SpringUtil.getApplicationContext() == null) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            EasyCacheListener listener = SpringUtil.getBean(EasyCacheListener.class);
            listener.registerMessageHandler(messageKey(), this);
            initCache();
            logger.info("CacheService初始化成功");
        }).start();
    }

    /**
     * 业务区分标识<br>
     *
     * @return
     */
    String messageKey();

    /**
     * 初始化缓存
     */
    void initCache();

    /**
     * 刷新全部缓存
     */
    void refreshAll();

    /**
     * 刷新部分缓存
     *
     * @param ids 主键集合
     */
    void refreshPart(List<String> ids);
}