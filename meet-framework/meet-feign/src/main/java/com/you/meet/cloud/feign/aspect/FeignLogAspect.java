package com.you.meet.cloud.feign.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author zhoujunlin
 * @date 2023/03/28
 **/
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "feign.log.enable", havingValue = "true", matchIfMissing = true)
public class FeignLogAspect {

    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void feignClientPointcut() {
    }

    @Around("feignClientPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        log.warn("FeignClient接口执行路径: {}#{}", signature.getDeclaringType().getSimpleName(), signature.getName());
        log.warn("请求外部接口入参:{}", JSONObject.toJSONString(pjp.getArgs()));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retObj = pjp.proceed();
        stopWatch.stop();
        log.warn("请求外部接口耗时: {}ms", stopWatch.getTotalTimeMillis());

        log.warn("外部接口响应:{}", JSONObject.toJSONString(retObj));
        return retObj;
    }

}
