package com.you.meet.cloud.starter.web.annotation;

import com.you.meet.cloud.starter.web.constant.CallType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 调用时间
 *
 * @author zhoujl
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CallTime {

    /**
     * 接口/方法/数据库表 名字
     */
    String name();

    /**
     * 类型 interface:接口(默认) method:方法 database:数据库
     */
    CallType type() default CallType.METHOD;
}
