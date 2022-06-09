package com.you.meet.nice.starter.web.interceptor;

import com.you.meet.nice.lib.common.exception.CommonErrorCode;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.lib.common.pojo.JsonResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: zhoujl
 * <p>
 * 拦截器的基类
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

    protected void failed(HttpServletResponse response, String message) throws Exception {
        JsonResponse jsonResponse = JsonResponse.builder().code(CommonErrorCode.ERR_5000.getCode()).msg(message).build();
        String result = JsonUtil.parseObj2Str(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");
        pw.write(result);
    }

    protected void fail(HttpServletResponse response, String redirectUrl) throws Exception {
        JsonResponse jsonResponse = JsonResponse.builder().code(CommonErrorCode.ERR_5000.getCode()).data(redirectUrl).build();
        String result = JsonUtil.parseObj2Str(jsonResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");
        pw.write(result);
    }
}
