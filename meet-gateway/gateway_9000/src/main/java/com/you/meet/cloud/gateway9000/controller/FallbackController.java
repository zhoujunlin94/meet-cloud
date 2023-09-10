package com.you.meet.cloud.gateway9000.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年09月10日 22:04
 * @desc
 */
@RestController
public class FallbackController {

    @GetMapping("/fb")
    public String fallback() {
        return "This is a gateway fallback";
    }

}
