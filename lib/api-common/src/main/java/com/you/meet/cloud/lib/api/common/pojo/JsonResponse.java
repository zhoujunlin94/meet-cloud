package com.you.meet.cloud.lib.api.common.pojo;

import com.you.meet.cloud.lib.api.common.exception.CommonErrorCode;
import com.you.meet.cloud.lib.api.common.util.JsonUtil;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author zhoujl
 * @Date 2020/5/3 19:02
 * @Description json格式响应
 **/
@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
public class JsonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private Object data;
    private String msg;

    public static JsonResponse ok() {
        return JsonResponse.builder().code(CommonErrorCode.SUC.getCode()).msg(CommonErrorCode.SUC.getMsg()).build();
    }

    public static JsonResponse ok(String msg) {
        return JsonResponse.builder().code(CommonErrorCode.SUC.getCode()).msg(msg).build();
    }

    public static JsonResponse ok(Object data) {
        return JsonResponse.builder().code(CommonErrorCode.SUC.getCode()).msg(CommonErrorCode.SUC.getMsg()).data(data).build();
    }

    public static JsonResponse fail(CommonErrorCode errorCode) {
        return JsonResponse.builder().code(errorCode.getCode()).msg(errorCode.getMsg()).build();
    }

    public static JsonResponse fail(String msg) {
        return JsonResponse.builder().code(CommonErrorCode.FAIL.getCode()).msg(msg).build();
    }

    public static JsonResponse fail(CommonErrorCode errorCode, Object data) {
        return JsonResponse.builder().code(errorCode.getCode()).msg(errorCode.getMsg()).data(data).build();
    }

    @Override
    public String toString() {
        return JsonUtil.parseObj2Str(this);
    }
}
