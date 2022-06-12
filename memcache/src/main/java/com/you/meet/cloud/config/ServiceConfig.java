package com.you.meet.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoujunlin
 * @date 2022年06月11日 21:48
 * @desc
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "service-conf")
public class ServiceConfig {

}
