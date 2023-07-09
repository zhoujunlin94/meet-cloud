package com.you.meet.cloud.web.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2022年07月19日 22:17
 * @desc
 */
@Slf4j
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    /**
     * spring上下文应用环境
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> targetClz) {
        T beanInstance = null;
        //优先按type查
        try {
            beanInstance = (T) ApplicationContextHelper.applicationContext.getBean(targetClz);
        } catch (Exception e) {
            log.warn("优先根据type查找失败", e);
        }
        //按name查
        if (Objects.isNull(beanInstance)) {
            String simpleName = targetClz.getSimpleName();
            //首字母小写
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            beanInstance = (T) ApplicationContextHelper.applicationContext.getBean(simpleName);
        }
        if (Objects.isNull(beanInstance)) {
            throw new RuntimeException("Component " + targetClz + " can not be found in Spring Container");
        }
        return beanInstance;
    }

    public static Object getBean(String clzName) {
        return ApplicationContextHelper.applicationContext.getBean(clzName);
    }

    public static <T> T getBean(String clzName, Class<T> requiredType) {
        return ApplicationContextHelper.applicationContext.getBean(clzName, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... params) {
        return ApplicationContextHelper.applicationContext.getBean(requiredType, params);
    }

    public static boolean containsBean(String name) {
        return ApplicationContextHelper.applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return ApplicationContextHelper.applicationContext.isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return ApplicationContextHelper.applicationContext.getType(name);
    }

    public static <T> Map<String, T> getImplInstance(Class<T> clazz) {
        return ApplicationContextHelper.applicationContext.getBeansOfType(clazz);
    }

    public static <T> List<T> getImplInstanceArray(Class<T> clazz) {
        Map<String, T> map = ApplicationContextHelper.applicationContext.getBeansOfType(clazz);
        return new ArrayList<>(map.values());
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
