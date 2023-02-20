package com.you.meet.cloud.provider.biz.controller;

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
    public String echo(@PathVariable("msg") String msg) {
        return msg;
    }


}