package com.you.meet.cloud.consumer.feign.client;

import com.you.meet.nice.lib.common.pojo.JsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhoujunlin
 * @date 2022年07月21日 17:15
 * @desc
 */
@FeignClient(value = "memcache")
public interface HealthService {

    @GetMapping("/health/okJson")
    JsonResponse okJson();

}
