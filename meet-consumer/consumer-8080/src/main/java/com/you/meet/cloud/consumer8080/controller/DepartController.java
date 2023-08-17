package com.you.meet.cloud.consumer8080.controller;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer8080.dto.DepartDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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
    private static final String SERVICE_PROCIER = "http://localhost:8081/depart";

    @PostMapping("/save")
    public JSONResponse save(@RequestBody DepartDTO depart) {
        String url = SERVICE_PROCIER + "/save";
        return restTemplate.postForObject(url, depart, JSONResponse.class);
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable("id") int id) {
        restTemplate.delete(SERVICE_PROCIER + "/del/" + id);
    }

    @PutMapping("/update")
    public void update(@RequestBody DepartDTO depart) {
        String url = SERVICE_PROCIER + "/update";
        restTemplate.put(url, depart);
    }

    @GetMapping("/get/{id}")
    public JSONResponse get(@PathVariable("id") int id) {
        String url = SERVICE_PROCIER + "/get/" + id;
        return restTemplate.getForObject(url, JSONResponse.class);
    }

    @GetMapping("/list")
    public JSONResponse list() {
        String url = SERVICE_PROCIER + "/list";
        return restTemplate.getForObject(url, JSONResponse.class);
    }

}
