package com.you.meet.cloud.consumer.biz.config;

import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.common.pojo.RequestContext;
import com.you.meet.cloud.common.util.RequestIdUtil;
import com.you.meet.cloud.common.util.ThreadLocalUtil;
import feign.RequestInterceptor;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            RequestContext requestContext = (RequestContext) ThreadLocalUtil.get();
            if (Objects.nonNull(requestContext)) {
                requestId = requestContext.getRequestId();
            }
            requestId = StrUtil.isBlank(requestId) ? RequestIdUtil.requestId() : requestId;
            requestTemplate.header(RequestIdUtil.REQUEST_ID, requestId);
        };
    }

}
