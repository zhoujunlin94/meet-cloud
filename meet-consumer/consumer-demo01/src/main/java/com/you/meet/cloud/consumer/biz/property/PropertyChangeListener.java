package com.you.meet.cloud.consumer.biz.property;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月07日 15:11
 * @desc
 */
@Slf4j
@Component
public class PropertyChangeListener implements ApplicationListener<EnvironmentChangeEvent> {

    @Resource
    private ConfigurableEnvironment environment;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        for (String key : event.getKeys()) {
            log.info("[onApplicationEvent][key({})最新的value为{}]", key, environment.getProperty(key));
        }
    }
}
