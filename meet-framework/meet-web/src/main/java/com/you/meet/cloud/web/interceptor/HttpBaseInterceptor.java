package com.you.meet.cloud.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.common.pojo.RequestContext;
import com.you.meet.cloud.common.util.RequestContextUtil;
import com.you.meet.cloud.common.util.RequestIdUtil;
import com.you.meet.cloud.common.util.ServletUtils;
import com.you.meet.cloud.web.helper.ProjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
public class HttpBaseInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader(RequestIdUtil.REQUEST_ID);
        if (StrUtil.isBlank(requestId)) {
            requestId = RequestIdUtil.requestId();
        }
        RequestContext requestContext = RequestContext.builder().requestId(requestId).clientIP(ServletUtils.getClientIP()).build();
        RequestContextUtil.set(requestContext);

        MDC.put(RequestIdUtil.REQUEST_ID, requestId);
        log.info("开始访问:{}", request.getRequestURL().toString());
        log.info("ip地址:{}", requestContext.getClientIP());
        request.setAttribute("startTime", System.currentTimeMillis());
        request.setAttribute("ctx", ProjectHelper.getProjectBasePath(request));
        response.addHeader(RequestIdUtil.REQUEST_ID, requestId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            long start = (long) request.getAttribute("startTime");
            long end = System.currentTimeMillis();
            log.info("结束加载请求:{},总用时:{}ms", request.getRequestURL().toString(), end - start);
        } finally {
            MDC.remove(RequestIdUtil.REQUEST_ID);
            RequestContextUtil.remove();
        }
    }
}
