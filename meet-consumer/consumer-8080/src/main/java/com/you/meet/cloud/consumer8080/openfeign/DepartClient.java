package com.you.meet.cloud.consumer8080.openfeign;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer8080.dto.DepartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhoujunlin
 * @date 2023年09月03日 16:24
 * @desc
 */
@FeignClient(value = "provider-depart", path = "/depart")
public interface DepartClient {

    @PostMapping("/save")
    JSONResponse save(@RequestBody DepartDTO depart);

    @DeleteMapping("/del/{id}")
    JSONResponse delete(@PathVariable("id") int id);

    @PutMapping("/update")
    JSONResponse update(@RequestBody DepartDTO depart);

    @GetMapping("/get/{id}")
    JSONResponse get(@PathVariable("id") int id);

    @GetMapping("/list")
    JSONResponse list();

}
