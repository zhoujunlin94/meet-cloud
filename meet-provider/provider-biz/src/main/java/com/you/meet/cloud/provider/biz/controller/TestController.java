package com.you.meet.cloud.provider.biz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年02月19日 20:27
 * @desc
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/echo/{msg}")
    public Map<String, Object> echo(@PathVariable("msg") String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", msg);
        resultMap.put("start_date", new Date());
        return resultMap;
    }

}
