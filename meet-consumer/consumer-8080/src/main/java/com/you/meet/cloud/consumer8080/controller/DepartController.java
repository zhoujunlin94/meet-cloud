package com.you.meet.cloud.consumer8080.controller;

import com.you.meet.cloud.consumer8080.dto.DepartDTO;
import com.you.meet.nice.common.pojo.JsonResponse;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年08月17日 23:05
 * @desc
 */
@RestController
@RequestMapping("/depart")
public class DepartController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;

    // 通过ip的方式访问
    // private static final String SERVICE_PROCIER = "http://localhost:8081/depart";
    // 通过微服务名的方式访问  微服务命名不能有下划线！！！  否则restTemplate调用识别不到
    private static final String SERVICE_PROVIDER = "http://provider-depart/depart";

    @PostMapping("/save")
    public JsonResponse save(@RequestBody DepartDTO depart) {
        String url = SERVICE_PROVIDER + "/save";
        return restTemplate.postForObject(url, depart, JsonResponse.class);
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable("id") int id) {
        restTemplate.delete(SERVICE_PROVIDER + "/del/" + id);
    }

    @PutMapping("/update")
    public void update(@RequestBody DepartDTO depart) {
        String url = SERVICE_PROVIDER + "/update";
        restTemplate.put(url, depart);
    }

    @GetMapping("/get/{id}")
    public JsonResponse get(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/get/" + id;
        return restTemplate.getForObject(url, JsonResponse.class);
    }

    @GetMapping("/list")
    public JsonResponse list() {
        String url = SERVICE_PROVIDER + "/list";
        return restTemplate.getForObject(url, JsonResponse.class);
    }


    @GetMapping("/discovery")
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
