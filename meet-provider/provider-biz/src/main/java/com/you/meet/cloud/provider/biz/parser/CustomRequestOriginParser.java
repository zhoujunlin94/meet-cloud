package com.you.meet.cloud.provider.biz.parser;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年02月21日 16:04
 * @desc
 */
@Component
public class CustomRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        /**
         * 测试sentinel的黑白名单授权功能
         */
        String originClient = request.getHeader("origin_client");
        return StrUtil.isBlank(originClient) ? "default" : originClient;
    }

}
