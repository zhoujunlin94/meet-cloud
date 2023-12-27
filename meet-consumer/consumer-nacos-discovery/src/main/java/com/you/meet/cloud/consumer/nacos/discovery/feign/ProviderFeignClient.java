package com.you.meet.cloud.consumer.nacos.discovery.feign;

import com.you.meet.nice.common.pojo.JsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhoujunlin
 * @date 2023年12月27日 11:31
 * @desc
 */
@FeignClient(value = "provider-nacos-discovery")
public interface ProviderFeignClient {

    @GetMapping("/provider/headers")
    JsonResponse headers();

}
