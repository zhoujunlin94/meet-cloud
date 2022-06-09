package com.you.meet.cloud.starter.redis.aop;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.starter.redis.annotation.Limit;
import com.you.meet.cloud.starter.redis.constant.LimitType;
import com.you.meet.cloud.starter.redis.constant.LuaScripts;
import com.you.meet.cloud.starter.redis.service.RedisService;
import com.you.meet.nice.lib.common.exception.CommonErrorCode;
import com.you.meet.nice.lib.common.exception.MeetException;
import com.you.meet.nice.lib.common.util.IPUtil;
import com.you.meet.nice.lib.common.util.ProjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

/**
 * 接口限流
 *
 * @author zhoujl
 */
@Slf4j
@Lazy
@Aspect
@Component
public class LimitAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.you.meet.cloud.starter.redis.annotation.Limit)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ProjectUtil.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        String key;
        String ip = IPUtil.getIpAddr(request);
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();
        switch (limitType) {
            case IP:
                key = ip;
                break;
            case NORMAL:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        String keys = StrUtil.join(":", limitAnnotation.prefix(), key, ip);
        Long count = redisService.executeLua(LuaScripts.LUA_SCRIPTS, Collections.singletonList(keys), Arrays.asList(Convert.toStr(limitCount), Convert.toStr(limitPeriod)), Long.class);
        log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);
        if (count != null && count <= limitCount) {
            return point.proceed();
        } else {
            throw MeetException.meet(CommonErrorCode.ERR_5001);
        }
    }

}
