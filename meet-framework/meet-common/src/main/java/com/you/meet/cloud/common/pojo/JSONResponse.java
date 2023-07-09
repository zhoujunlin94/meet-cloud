package com.you.meet.cloud.common.pojo;

import com.alibaba.fastjson.JSONObject;
import com.you.meet.cloud.common.exception.CommonErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zhoujl
 * @Date 2020/5/3 19:02
 * @Description json格式响应
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JSONResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private Object data;
    private String msg;

    public static JSONResponse ok() {
        return JSONResponse.builder().code(CommonErrorCode.S_SUC.getCode()).msg(CommonErrorCode.S_SUC.getMsg()).build();
    }

    public static JSONResponse ok(Object data) {
        return JSONResponse.builder().code(CommonErrorCode.S_SUC.getCode()).msg(CommonErrorCode.S_SUC.getMsg()).data(data).build();
    }

    public static JSONResponse fail(CommonErrorCode errorCode) {
        return JSONResponse.builder().code(errorCode.getCode()).msg(errorCode.getMsg()).build();
    }

    public static JSONResponse fail(String msg) {
        return JSONResponse.builder().code(CommonErrorCode.S_FAIL.getCode()).msg(msg).build();
    }

    public static JSONResponse fail(CommonErrorCode errorCode, Object data) {
        return JSONResponse.builder().code(errorCode.getCode()).msg(errorCode.getMsg()).data(data).build();
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
