package io.github.zhoujunlin94.cloud.consumer.nacos.discovery.controller;

import io.github.zhoujunlin94.cloud.consumer.nacos.discovery.feign.ProviderFeignClient;
import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年12月26日 20:05
 * @desc
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/consumer")
public class ConsumerController {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final ProviderFeignClient providerFeignClient;

    @GetMapping("/headers")
    public JsonResponse getHeaders() {
        String url = String.format("http://%s/provider/headers", "provider-nacos-discovery");
        System.out.println("request url:" + url);
        return restTemplate.getForObject(url, JsonResponse.class);
    }

    @GetMapping("/headers_two")
    public JsonResponse getHeader2() {
        return providerFeignClient.headers();
    }

    @GetMapping("/services")
    public List<String> discovery() {
        return discoveryClient.getServices();
    }

    @GetMapping("/instances")
    public Map<String, List<ServiceInstance>> instances() {
        Map<String, List<ServiceInstance>> ret = new HashMap<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            ret.put(service, instances);
        }
        return ret;
    }

}
