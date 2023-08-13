package com.you.meet.cloud.web.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023/03/28
 **/
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "endpoint.log.enable", havingValue = "true", matchIfMissing = true)
public class EndpointLogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void endpointMethodPointcut() {
    }

    @Around("endpointMethodPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        logMethodInfo(pjp);
        return pjp.proceed();
    }

    private void logMethodInfo(ProceedingJoinPoint pjp) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

            String className = pjp.getSignature().getDeclaringType().getSimpleName();
            String methodName = pjp.getSignature().getName();
            log.warn("接口执行路径: {}#{}", className, methodName);

            Enumeration<String> headerNames = request.getHeaderNames();
            Map<String, Object> headerMap = new HashMap<>();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headerMap.put(headerName, request.getHeader(headerName));
            }
            if (CollUtil.isNotEmpty(headerMap)) {
                log.warn("Headers:\n{}", JSONObject.toJSONString(headerMap));
            }

            String method = request.getMethod();
            if (Method.POST.toString().equalsIgnoreCase(method) || Method.PUT.toString().equalsIgnoreCase(method)) {
                Object[] args = pjp.getArgs();
                for (Object arg : args) {
                    if (!(arg instanceof MultipartFile)) {
                        log.warn("RequestBody: {}", JSONObject.toJSONString(arg));
                    }
                }
            }

            Map<String, String[]> parameterMap = request.getParameterMap();
            if (CollUtil.isNotEmpty(parameterMap)) {
                log.warn("ParameterMap: {}", JSONObject.toJSONString(parameterMap));
            }
            String queryString = request.getQueryString();
            if (StrUtil.isNotBlank(queryString)) {
                log.warn("QueryString: {}", queryString);
            }
        } catch (Exception ex) {
            log.error("EndpointLogAspect logMethodInfo Meet Exception:", ex);
        }
    }

}
