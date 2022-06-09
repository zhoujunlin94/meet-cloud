package com.you.meet.cloud.lib.api.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhoujl
 * @Date 2020/5/3 20:37
 * @Description
 **/
@Component
public class SpringUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clz) throws BeansException {
        return (T) applicationContext.getBean(clz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

    public static <T> Map<String, T> getImplInstance(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    public static <T> List<T> getImplInstanceArray(Class<T> clazz) {
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        return new ArrayList<>(map.values());
    }
}
