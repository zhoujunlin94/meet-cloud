package com.you.meet.cloud.consumer8080.openfeign;

import com.you.meet.cloud.consumer8080.dto.DepartDTO;
import com.you.meet.nice.common.pojo.JsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhoujunlin
 * @date 2023年09月03日 16:24
 * @desc
 */
@FeignClient(value = "provider-depart", path = "/depart")
public interface DepartClient {

    @PostMapping("/save")
    JsonResponse save(@RequestBody DepartDTO depart);

    @DeleteMapping("/del/{id}")
    JsonResponse delete(@PathVariable("id") int id);

    @PutMapping("/update")
    JsonResponse update(@RequestBody DepartDTO depart);

    @GetMapping("/get/{id}")
    JsonResponse get(@PathVariable("id") int id);

    @GetMapping("/list")
    JsonResponse list();

}
