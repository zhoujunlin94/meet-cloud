package com.you.meet.cloud.consumer.biz.feign.fallback;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer.biz.feign.client.ProviderBizClient;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 12:41
 * @desc
 */
public class ProviderBizClientFallback implements ProviderBizClient {

    private Throwable throwable;
    private JSONResponse fallbackResp;

    public ProviderBizClientFallback(Throwable throwable) {
        this.throwable = throwable;
        this.fallbackResp = JSONResponse.fail("fallback:" + throwable.getClass().getSimpleName());
    }

    @Override
    public JSONResponse echo(String msg) {
        return this.fallbackResp;
    }

    @Override
    public JSONResponse test() {
        return this.fallbackResp;
    }

    @Override
    public String echoName(String name) {
        return this.fallbackResp.getMsg();
    }
}
