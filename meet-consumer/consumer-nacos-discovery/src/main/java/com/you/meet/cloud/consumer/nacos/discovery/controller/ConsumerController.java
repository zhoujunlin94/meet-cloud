package com.you.meet.cloud.consumer.nacos.discovery.controller;

import com.you.meet.cloud.consumer.nacos.discovery.feign.ProviderFeignClient;
import com.you.meet.nice.common.pojo.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhoujunlin
 * @date 2023年12月26日 20:05
 * @desc
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/consumer")
public class ConsumerController {

    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate restTemplate;
    private final ProviderFeignClient providerFeignClient;

    @GetMapping("/headers_one")
    public JsonResponse getHeader() {
        //使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider-nacos-discovery");
        String url = String.format("http://%s:%s/provider/headers", serviceInstance.getHost(), serviceInstance.getPort());
        System.out.println("request url:" + url);
        return restTemplate.getForObject(url, JsonResponse.class);
    }


    @GetMapping("/headers_two")
    public JsonResponse getHeader2() {
        String url = String.format("http://%s/provider/headers", "provider-nacos-discovery");
        System.out.println("request url:" + url);
        return restTemplate.getForObject(url, JsonResponse.class);
    }

    @GetMapping("/headers_three")
    public JsonResponse getHeader3() {
        return providerFeignClient.headers();
    }

}
