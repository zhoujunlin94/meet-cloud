package com.you.meet.cloud.starter.web.interceptor;

import com.you.meet.cloud.lib.api.common.constant.CommonConstant;
import com.you.meet.cloud.lib.api.common.util.IPUtil;
import com.you.meet.cloud.lib.api.common.util.ProjectUtil;
import com.you.meet.cloud.lib.api.common.util.RequestIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoujl
 * @date 2021/4/22 18:09
 * @desc
 */
@Slf4j
@Lazy
@Component
public class HttpBaseInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(CommonConstant.REQUEST_ID, RequestIdUtil.requestId());
        log.info("开始访问:{}", request.getRequestURL().toString());
        log.info("ip地址:{}", IPUtil.getIpAddr(request));
        request.setAttribute("startTime", System.currentTimeMillis());
        request.setAttribute("ctx", ProjectUtil.getProjectBasePath(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (long) request.getAttribute("startTime");
        long end = System.currentTimeMillis();
        log.info("结束加载请求:{},总用时:{}ms", request.getRequestURL().toString(), end - start);
        MDC.remove(CommonConstant.REQUEST_ID);
    }
}
