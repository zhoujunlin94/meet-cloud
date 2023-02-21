package com.you.meet.cloud.provider.biz.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年02月19日 20:27
 * @desc
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/echo/{msg}")
    public Map<String, Object> echo(@PathVariable("msg") String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", msg);
        resultMap.put("start_date", new Date());
        return resultMap;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        // 测试熔断
        Thread.sleep(100L);
        return "哈哈哈sleep";
    }

    /**
     * 热点资源配置
     *
     * @param id
     * @return
     */
    @GetMapping("/product_info")
    @SentinelResource("demo_product_info_hot")
    public String productInfo(Integer id) {
        return "商品编号：" + id;
    }

}
