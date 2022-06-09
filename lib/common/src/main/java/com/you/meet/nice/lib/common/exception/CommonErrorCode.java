package com.you.meet.nice.lib.common.exception;

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
     */
    SUC(0, "正常返回"),

    UN_LOGIN(401, "未登录"),
    UN_AUTHORIZATION(403, "未授权"),
    NOT_FOUND(404, "资源不存在"),
    BAD_PARAMETER(405, "参数错误"),

    USER_NOT_FOUND(501, "用户名不存在"),
    USER_LOCK(502, "用户被冻结"),
    PASSWORD_ERROR(503, "用户名密码不正确"),

    FAIL(1, "失败"),

    ERR_5000(5000, "系统繁忙，请稍后再试!"),
    ERR_5001(5001, "接口访问超出频率限制"),
    ERR_5002(5002, "参数校验出错"),
    ;

    private final int code;
    private final String msg;

}
