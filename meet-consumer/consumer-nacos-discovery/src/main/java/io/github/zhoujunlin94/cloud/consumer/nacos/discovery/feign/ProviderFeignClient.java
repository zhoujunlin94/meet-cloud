package io.github.zhoujunlin94.cloud.consumer.nacos.discovery.feign;

import io.github.zhoujunlin94.meet.common.pojo.JsonResponse;
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
