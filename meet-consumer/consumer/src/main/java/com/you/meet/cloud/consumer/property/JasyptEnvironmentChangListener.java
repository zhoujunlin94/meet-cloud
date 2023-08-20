package com.you.meet.cloud.consumer.property;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月07日 15:39
 * @desc
 */
@Slf4j
@Component
public class JasyptEnvironmentChangListener implements ApplicationListener<EnvironmentChangeEvent> {

    /**
     * 实现配置项的获取和修改
     */
    @Resource
    private EnvironmentManager environmentManager;
    @Resource
    private StringEncryptor stringEncryptor;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        for (String key : event.getKeys()) {
            Object propertyValue = environmentManager.getProperty(key);
            if (!(propertyValue instanceof String)) {
                continue;
            }
            String propertyStr = (String) propertyValue;
            if (propertyStr.startsWith("ENC(") && propertyStr.endsWith(")")) {
                String decryptValue = stringEncryptor.decrypt(StrUtil.subBetween(propertyStr, "ENC(", ")"));
                log.info("[onApplicationEvent][key({})解密后为{}]", key, decryptValue);
                environmentManager.setProperty(key, decryptValue);
            }
        }
    }
}
