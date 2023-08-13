package com.you.meet.cloud.common.util;

import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ClassUtils;

/**
 * @author zhoujunlin
 * @date 2023年07月09日 17:08
 * @desc
 */
public class ReflectUtils {

    public static Class<?> getSuperClassGenericType(Class<?> clazz, Class<?> genericIfc, int index) {
        Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(ClassUtils.getUserClass(clazz), genericIfc);
        return null == typeArguments ? null : typeArguments[index];
    }


}
