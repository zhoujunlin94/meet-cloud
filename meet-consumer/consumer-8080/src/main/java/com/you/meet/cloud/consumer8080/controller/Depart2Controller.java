package com.you.meet.cloud.consumer8080.controller;

import com.you.meet.cloud.consumer8080.dto.DepartDTO;
import com.you.meet.cloud.consumer8080.openfeign.DepartClient;
import com.you.meet.nice.common.pojo.JsonResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年08月17日 23:05
 * @desc
 */
@RestController
@RequestMapping("/depart2")
public class Depart2Controller {

    @Resource
    private DepartClient departClient;

    @PostMapping("/save")
    public JsonResponse save(@RequestBody DepartDTO depart) {
        return departClient.save(depart);
    }

    @DeleteMapping("/del/{id}")
    public JsonResponse delete(@PathVariable("id") int id) {
        return departClient.delete(id);
    }

    @PutMapping("/update")
    public JsonResponse update(@RequestBody DepartDTO depart) {
        return departClient.update(depart);
    }

    @GetMapping("/get/{id}")
    public JsonResponse get(@PathVariable("id") int id) {
        return departClient.get(id);
    }

    @GetMapping("/list")
    public JsonResponse list() {
        return departClient.list();
    }

}
