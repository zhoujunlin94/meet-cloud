package com.you.meet.cloud.consumer.controller;

import cn.hutool.core.collection.CollUtil;
import com.you.meet.cloud.consumer.feign.client.ProviderDemo01Client;
import com.you.meet.nice.common.exception.MeetException;
import com.you.meet.nice.common.pojo.JsonResponse;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年02月19日 21:08
 * @desc
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private ProviderDemo01Client providerClient;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/echo/{msg}")
    public JsonResponse echo(@PathVariable("msg") String msg) {
        // 获得provicer-biz的一个实例
        ServiceInstance serviceInstance = CollUtil.getFirst(discoveryClient.getInstances("provider-demo01"));
        if (Objects.isNull(serviceInstance)) {
            throw MeetException.meet("获取不到实例");
        }
        String targetUrl = serviceInstance.getUri() + "/test/echo/" + msg;
        return restTemplate.getForObject(targetUrl, JsonResponse.class);
    }

    @GetMapping("/echo2/{msg}")
    public JsonResponse echo2(@PathVariable("msg") String msg) {
        return providerClient.echo(msg);
    }

    @GetMapping("/test")
    public JsonResponse test() {
        return providerClient.test();
    }

    @GetMapping("/echoName")
    public String echoName(@RequestParam String name) {
        String resp = providerClient.echoName(name);
        return "consumer: " + resp;
    }

}
