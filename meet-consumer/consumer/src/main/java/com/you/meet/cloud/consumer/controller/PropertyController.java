package com.you.meet.cloud.consumer.controller;

import com.you.meet.cloud.consumer.property.OrderProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年03月07日 14:31
 * @desc
 */
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Resource
    private OrderProperty orderProperty;

    // @Value 不会配置自动刷新 除非添加@RefreshScope注解
    // @NacosValue(value = "${order.pay-timeout-seconds}")
    @Value(value = "${order.pay-timeout-seconds}")
    private Integer payTimeoutSeconds;
    // @NacosValue(value = "${order.create-frequency-seconds}")
    @Value(value = "${order.create-frequency-seconds}")
    private Integer createFrequencySeconds;

    /**
     * 测试 @Value 注解的属性
     */
    @GetMapping("/test02")
    public Map<String, Object> test02() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("payTimeoutSeconds", payTimeoutSeconds);
        resultMap.put("createFrequencySeconds", createFrequencySeconds);
        return resultMap;
    }

    @GetMapping("/test1")
    public OrderProperty test1() {
        return orderProperty;
    }

}
