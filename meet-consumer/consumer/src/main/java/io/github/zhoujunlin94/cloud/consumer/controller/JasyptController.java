package io.github.zhoujunlin94.cloud.consumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2023年03月07日 15:33
 * @desc
 */
@RefreshScope
@RestController
@RequestMapping("/jasypt")
public class JasyptController {

    @Value("${mima:}")
    private String value;

    @GetMapping("/read")
    public String getValue() {
        // nacos更新配置之后返回了ENC(sTOLBlxfq3m4fCLuGNChnIBCdenciAmk)整个字符串 即没有解密
        // 需要新增JasyptEnvironmentChangeListener处理解密
        return value;
    }

}
