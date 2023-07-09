package com.you.meet.cloud.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoujl
 * @date 2021/3/31 15:32
 * @desc
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    /**
     * 错误码描述
     * S开头 系统错误信息
     * P开头 参数错误信息
     * B开头 业务错误信息
     */
    S_SUC(0, "success"),
    S_FAIL(1, "失败"),
    S_SYSTEM_BUSY(5000, "系统繁忙，请稍后再试!"),
    S_OVER_CALLED(5001, "接口访问超出频率限制"),

    B_UN_LOGIN(4001, "未登录"),
    B_UN_AUTHORIZATION(4003, "未授权"),
    B_NOT_FOUND(4004, "数据不存在"),
    B_AUTHORIZATION_FAILED(4005, "授权失败"),

    P_BAD_PARAMETER(4005, "参数错误"),
    P_PARAM_CHECK_ERROR(4006, "参数校验出错"),

    B_USER_NOT_FOUND(5001, "用户名不存在"),
    B_USER_LOCK(5002, "用户被冻结"),
    B_PASSWORD_ERROR(5003, "用户名密码不正确"),

    ;

    private final int code;
    private final String msg;

}
