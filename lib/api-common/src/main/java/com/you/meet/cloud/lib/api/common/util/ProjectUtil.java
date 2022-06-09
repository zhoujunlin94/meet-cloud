package com.you.meet.cloud.lib.api.common.util;

import com.you.meet.cloud.lib.api.common.pojo.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @Author zhoujl
 * @Date 2020/5/3 21:46
 * @Description 项目的基本工具类
 **/
@Slf4j
public class ProjectUtil {

    public static String getProjectBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 判断是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    /**
     * 输出json
     *
     * @param response
     * @param jsonResponse
     */
    public static void responseJson(HttpServletResponse response, JsonResponse jsonResponse) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(jsonResponse.toString());
        } catch (Exception e) {
            log.error("【JSON输出异常】", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
