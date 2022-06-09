package com.you.meet.nice.starter.web.aop;

import cn.hutool.core.lang.Snowflake;
import com.you.meet.nice.starter.web.annotation.AsyncMdc;
import com.you.meet.nice.starter.web.annotation.CallTime;
import com.you.meet.nice.starter.web.constant.CallType;
import com.you.meet.nice.lib.common.constant.CommonConstant;
import com.you.meet.nice.lib.common.util.RequestIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.NDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author zhoujl
 * @Date 2020/5/4 12:22
 * @Description 日志打印aop
 **/
@Aspect
@Order(-100)
@Component
@Slf4j
public class LogbackAspect {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private Snowflake snowflake;

    @Before("@annotation(callTime)")
    public void before(JoinPoint joinPoint, CallTime callTime) throws Throwable {
        NDC.push(appName + "_" + snowflake.nextId() + "_" + callTime.name());
    }

    @Before("@annotation(asyncMdc)")
    public void before(JoinPoint joinPoint, AsyncMdc asyncMdc) throws Throwable {
        MDC.put(CommonConstant.REQUEST_ID, RequestIdUtil.requestId());
    }

    @After("@annotation(callTime)")
    public void after(JoinPoint point, CallTime callTime) {
        NDC.pop();
    }

    @After("@annotation(asyncMdc)")
    public void after(JoinPoint point, AsyncMdc asyncMdc) {
        MDC.remove(CommonConstant.REQUEST_ID);
    }

    /**
     * 统计方法执行耗时Around环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("@annotation(callTime)")
    public Object timeAround(ProceedingJoinPoint joinPoint, CallTime callTime) {
        //获取开始执行的时间
        long startTime = System.currentTimeMillis();
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("=====>统计某方法执行耗时环绕通知出错", e);
        }
        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        // 打印耗时的信息
        long time = endTime - startTime;
        if (CallType.INTERFACE == callTime.type()) {
            log.info("接口{}调用共耗时：{} ms", callTime.name(), time);
        } else if (CallType.DATABASE == callTime.type()) {
            log.info("查询数据库表：{} 共耗时：{} ms", callTime.name(), time);
        } else if (CallType.METHOD == callTime.type()) {
            log.info("方法{}运行共耗时：{} ms", callTime.name(), time);
        }
        return obj;
    }
}