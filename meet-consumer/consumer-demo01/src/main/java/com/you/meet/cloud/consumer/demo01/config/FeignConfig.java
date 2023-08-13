package com.you.meet.cloud.consumer.demo01.config;

import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.common.pojo.RequestContext;
import com.you.meet.cloud.common.util.RequestContextUtil;
import com.you.meet.cloud.common.util.RequestIdUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年03月14日 17:19
 * @desc
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignHeaderInterceptor() {
        return requestTemplate -> {
            String requestId = StrUtil.EMPTY;
            RequestContext requestContext = RequestContextUtil.get();
            if (Objects.nonNull(requestContext)) {
                requestId = requestContext.getRequestId();
            }
            requestId = StrUtil.isBlank(requestId) ? RequestIdUtil.requestId() : requestId;
            requestTemplate.header(RequestIdUtil.REQUEST_ID, requestId);
        };
    }

}
