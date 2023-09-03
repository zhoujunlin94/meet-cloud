package com.you.meet.cloud.consumer8080.controller;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer8080.dto.DepartDTO;
import com.you.meet.cloud.consumer8080.openfeign.DepartClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public JSONResponse save(@RequestBody DepartDTO depart) {
        return departClient.save(depart);
    }

    @DeleteMapping("/del/{id}")
    public JSONResponse delete(@PathVariable("id") int id) {
        return departClient.delete(id);
    }

    @PutMapping("/update")
    public JSONResponse update(@RequestBody DepartDTO depart) {
        return departClient.update(depart);
    }

    @GetMapping("/get/{id}")
    public JSONResponse get(@PathVariable("id") int id) {
        return departClient.get(id);
    }

    @GetMapping("/list")
    public JSONResponse list() {
        return departClient.list();
    }

}
