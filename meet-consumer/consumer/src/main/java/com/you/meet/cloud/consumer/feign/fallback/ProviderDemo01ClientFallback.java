package com.you.meet.cloud.consumer.feign.fallback;

import com.you.meet.cloud.common.pojo.JSONResponse;
import com.you.meet.cloud.consumer.feign.client.ProviderDemo01Client;

/**
 * @author zhoujunlin
 * @date 2023年02月25日 12:41
 * @desc
 */
public class ProviderDemo01ClientFallback implements ProviderDemo01Client {

    private Throwable throwable;
    private JSONResponse fallbackResp;

    public ProviderDemo01ClientFallback(Throwable throwable) {
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
