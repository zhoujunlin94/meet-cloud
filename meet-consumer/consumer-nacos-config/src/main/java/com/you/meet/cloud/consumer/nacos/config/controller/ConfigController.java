package com.you.meet.cloud.consumer.nacos.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年12月26日 17:49
 * @desc
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${depart.name}")
    private String departName;

    @GetMapping("/departName")
    public String departName() {
        return this.departName;
    }

}
