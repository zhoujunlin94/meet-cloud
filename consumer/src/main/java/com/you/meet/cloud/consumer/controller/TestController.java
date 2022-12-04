package com.you.meet.cloud.consumer.controller;

import com.you.meet.cloud.consumer.feign.client.MemcacheClient;
import com.you.meet.nice.lib.common.pojo.JsonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2022年12月04日 18:59
 * @desc
 */
@RefreshScope
@RestController
@RequestMapping
public class TestController {

    @Resource
    private MemcacheClient memcacheClient;

    @Value("${username}")
    private String username;
    @Value("${userage}")
    private Integer userage;

    @GetMapping("/nacosConfig")
    public JsonResponse nacosConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("userage", userage);
        config.put("username", username);
        return JsonResponse.ok().setData(config);
    }

    @GetMapping("/okJson")
    public JsonResponse okJson() {
        return memcacheClient.okJson();
    }

}
