package com.you.meet.cloud.consumer.demo01.feign.fallback.factory;

import com.you.meet.cloud.consumer.demo01.feign.fallback.ProviderDemo01ClientFallback;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 12:42
 * @desc
 */
@Component
public class ProviderDemo01ClientFallbackFactory implements FallbackFactory<ProviderDemo01ClientFallback> {

    @Override
    public ProviderDemo01ClientFallback create(Throwable cause) {
        // 开启 Sentinel 对 Feign 的支持，默认为 false 关闭。
        return new ProviderDemo01ClientFallback(cause);
    }

}
