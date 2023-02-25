package com.you.meet.cloud.consumer.biz.controller;

import cn.hutool.core.collection.CollUtil;
import com.you.meet.cloud.common.exception.MeetException;
import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer.biz.feign.client.ProviderBizClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private ProviderBizClient providerClient;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/echo/{msg}")
    public JSONResponse echo(@PathVariable("msg") String msg) {
        // 获得provicer-biz的一个实例
        ServiceInstance serviceInstance = CollUtil.getFirst(discoveryClient.getInstances("provider-biz"));
        if (Objects.isNull(serviceInstance)) {
            throw MeetException.meet("获取不到实例");
        }
        String targetUrl = serviceInstance.getUri() + "/test/echo/" + msg;
        return restTemplate.getForObject(targetUrl, JSONResponse.class);
    }

    @GetMapping("/echo2/{msg}")
    public JSONResponse echo2(@PathVariable("msg") String msg) {
        return providerClient.echo(msg);
    }

    @GetMapping("/test")
    public JSONResponse test() {
        return providerClient.test();
    }

}
