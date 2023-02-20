package com.you.meet.cloud.web.interceptor;

import com.you.meet.cloud.common.exception.CommonErrorCode;
import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.common.util.JsonUtil;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author: zhoujl
 * <p>
 * 拦截器的基类
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

    protected void failed(HttpServletResponse response, String message) throws Exception {
        JSONResponse jsonResponse = JSONResponse.builder().code(CommonErrorCode.S_SYSTEM_BUSY.getCode()).msg(message).build();
        String result = JsonUtil.parseObj2Str(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");
        pw.write(result);
    }

    protected void fail(HttpServletResponse response, String redirectUrl) throws Exception {
        JSONResponse jsonResponse = JSONResponse.builder().code(CommonErrorCode.S_SYSTEM_BUSY.getCode()).data(redirectUrl).build();
        String result = JsonUtil.parseObj2Str(jsonResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");
        pw.write(result);
    }
}
