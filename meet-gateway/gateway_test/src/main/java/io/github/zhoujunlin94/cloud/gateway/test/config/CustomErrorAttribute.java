package io.github.zhoujunlin94.cloud.gateway.test.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年09月07日 22:57
 * @desc
 */
/*@Component*/
public class CustomErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("status", "404");
        ret.put("message", "对不起，未找到该资源");
        return ret;
    }

}
