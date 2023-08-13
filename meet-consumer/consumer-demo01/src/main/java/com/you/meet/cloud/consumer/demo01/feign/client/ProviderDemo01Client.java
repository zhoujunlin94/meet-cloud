package com.you.meet.cloud.consumer.demo01.feign.client;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer.demo01.feign.fallback.factory.ProviderDemo01ClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhoujunlin
 * @date 2023年02月20日 16:35
 * @desc
 */
@FeignClient(name = "provider-demo01",
        fallbackFactory = ProviderDemo01ClientFallbackFactory.class)
public interface ProviderDemo01Client {

    @GetMapping("/test/echo/{msg}")
    JSONResponse echo(@PathVariable("msg") String msg);

    @GetMapping("/test/test")
    JSONResponse test();


    @GetMapping("/test/echo")
    String echoName(@RequestParam(value = "name") String name);

}
