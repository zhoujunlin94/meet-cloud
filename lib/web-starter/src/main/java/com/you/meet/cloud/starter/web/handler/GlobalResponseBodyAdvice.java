package com.you.meet.cloud.starter.web.handler;

import com.you.meet.cloud.lib.api.common.exception.CommonErrorCode;
import com.you.meet.cloud.lib.api.common.pojo.JsonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice(
        basePackages = {"com.you.meet.nice"}
)
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        body = body instanceof JsonResponse ? body : JsonResponse.builder().code(CommonErrorCode.SUC.getCode()).
                msg(CommonErrorCode.SUC.getMsg()).data(body).build();
        return body;
    }

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}

