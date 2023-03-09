package com.you.meet.cloud.provider.biz.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年02月19日 20:27
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping("/echo")
    public String echoName(@RequestParam String name) throws InterruptedException {
        // 模拟执行 100ms 时长。方便后续我们测试请求超时
        Thread.sleep(100L);

        // 记录被调用的日志
        log.info("[echo][被调用啦 name({})]", name);

        return serverPort + "-provider:" + name;
    }

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

    @GetMapping("/entry_demo")
    public String entryDemo() {
        Entry entry = null;
        try {
            // <1> 访问资源
            entry = SphU.entry("entry_demo");

            // <2> ... 执行业务逻辑

            return "执行成功";
        } catch (BlockException ex) { // <3>
            return "被拒绝";
        } finally {
            // <4> 释放资源
            if (entry != null) {
                entry.exit();
            }
        }
    }

    @GetMapping("/annotations_demo")
    @SentinelResource(value = "annotations_demo_resource",
            // 处理BlockException
            blockHandler = "blockHandler",
            // 处理所有Exception
            fallback = "fallback")
    public String annotationsDemo(@RequestParam(required = false) Integer id) throws InterruptedException {
        if (id == null) {
            throw new IllegalArgumentException("id 参数不允许为空");
        }
        return "success...";
    }

    // BlockHandler 处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String blockHandler(Integer id, BlockException ex) {
        return "block：" + ex.getClass().getSimpleName();
    }

    // Fallback 处理函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String fallback(Integer id, Throwable throwable) {
        return "fallback：" + throwable.getMessage();
    }

}
