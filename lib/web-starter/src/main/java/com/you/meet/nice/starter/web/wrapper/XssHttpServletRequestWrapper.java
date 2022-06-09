package com.you.meet.nice.starter.web.wrapper;

import com.you.meet.nice.lib.common.util.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * @author zhoujl
 * @date 2021/6/1 15:34
 * @desc 原文连接 https://blog.csdn.net/zzzgd_666/article/details/82870495
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 判断是否是上传 上传忽略
     */
    boolean isUpData = false;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        String contentType = request.getContentType();
        if (null != contentType) {
            isUpData = contentType.startsWith("multipart");
        }
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            value = JsoupUtil.clean(name, value);
        }
        return value;
    }

    /**
     * 覆盖getParameterValues方法
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtils.isNotEmpty(values)) {
            values = Stream.of(values).map(s -> JsoupUtil.clean(name, s)).toArray(String[]::new);
        }
        return values;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value != null) {
            value = JsoupUtil.clean(name, value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isUpData) {
            return super.getInputStream();
        } else {
            //处理原request的流中的数据
            byte[] bytes = inputHandlers(super.getInputStream()).getBytes();
            final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bais.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }
            };
        }

    }

    private String inputHandlers(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("出错了", e);
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                    log.error("出错了", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("出错了", e);
                }
            }
        }
        return JsoupUtil.cleanJson("json", sb.toString());
    }
}
