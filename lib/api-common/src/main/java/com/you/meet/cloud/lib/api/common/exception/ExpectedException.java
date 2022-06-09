package com.you.meet.cloud.lib.api.common.exception;

/**
 * @author zhoujl
 * 自定义异常  用于抛出想要的信息
 */
public class ExpectedException extends Exception {

    public ExpectedException() {
    }

    public ExpectedException(int code, String message) {
        super(message);
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
