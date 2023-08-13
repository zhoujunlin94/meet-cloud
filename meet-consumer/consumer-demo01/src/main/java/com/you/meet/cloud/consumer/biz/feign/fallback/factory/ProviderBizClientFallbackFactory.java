package com.you.meet.cloud.consumer.biz.feign.fallback.factory;

import com.you.meet.cloud.consumer.biz.feign.fallback.ProviderBizClientFallback;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 12:42
 * @desc
 */
@Component
public class ProviderBizClientFallbackFactory implements FallbackFactory<ProviderBizClientFallback> {

    @Override
    public ProviderBizClientFallback create(Throwable cause) {
        // 开启 Sentinel 对 Feign 的支持，默认为 false 关闭。
        return new ProviderBizClientFallback(cause);
    }

}
