package com.you.meet.cloud.consumer.controller;

import com.you.meet.cloud.consumer.feign.client.HealthService;
import com.you.meet.nice.lib.common.pojo.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2022年12月04日 18:59
 * @desc
 */
@RestController
@RequestMapping
public class TestController {

    @Resource
    private HealthService helloService;

    @GetMapping("/okJson")
    public JsonResponse okJson() {
        return helloService.okJson();
    }

}
