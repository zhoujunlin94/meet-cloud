package com.you.meet.cloud.provider.nacos.discovery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年12月26日 20:05
 * @desc
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @GetMapping("/headers")
    public Map<String, String> getHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, String> ret = new HashMap<>();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            StringBuilder headerValue = new StringBuilder();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                headerValue.append(headerValues.nextElement()).append(" ");
            }
            ret.put(headerName, headerValue.toString());
        }

        return ret;
    }

}
