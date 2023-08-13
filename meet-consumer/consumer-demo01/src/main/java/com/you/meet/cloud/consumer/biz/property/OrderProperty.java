package com.you.meet.cloud.consumer.biz.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年03月07日 14:29
 * @desc
 */
@Data
@Component
// 配置修改自动刷新
@ConfigurationProperties(prefix = "order")
// @NacosConfigurationProperties(prefix = "order", dataId = "${nacos.config.data-id}", type = ConfigType.YAML)
public class OrderProperty {

    /**
     * 订单支付超时时长，单位：秒。
     */
    private Integer payTimeoutSeconds;

    /**
     * 订单创建频率，单位：秒
     */
    private Integer createFrequencySeconds;

}
