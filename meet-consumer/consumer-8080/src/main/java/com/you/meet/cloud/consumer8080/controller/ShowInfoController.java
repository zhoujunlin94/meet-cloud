package com.you.meet.cloud.consumer8080.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 21:25
 * @desc
 */
@RestController
@RequestMapping("/info")
public class ShowInfoController {


    @GetMapping("/param")
    public Map<String, Object> header(@RequestParam String[] color,
                                      @RequestParam Integer size) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("color", color);
        ret.put("size", size);
        return ret;
    }

    @GetMapping("/headers")
    public Map<String, String> header(HttpServletRequest request) {
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

    @GetMapping("/header")
    public String header(@RequestHeader("X-Request-Color") String color) {
        return "X-Request-Color:" + color;
    }

}
