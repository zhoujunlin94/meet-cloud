package com.you.meet.cloud.consumer.biz.feign.client;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer.biz.feign.fallback.factory.ProviderBizClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhoujunlin
 * @date 2023年02月20日 16:35
 * @desc
 */
@FeignClient(name = "provider-biz",
        fallbackFactory = ProviderBizClientFallbackFactory.class)
public interface ProviderBizClient {

    @GetMapping("/test/echo/{msg}")
    JSONResponse echo(@PathVariable("msg") String msg);

    @GetMapping("/test/test")
    JSONResponse test();

}
