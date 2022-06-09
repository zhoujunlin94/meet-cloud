package com.you.meet.nice.lib.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author zhoujl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MeetException extends RuntimeException {

    private HttpStatus httpStatus;

    private int code;

    private String msg;

    private Object data;

    private MeetException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private MeetException(ErrorCode errorCode, Object data) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.data = data;
    }

    private MeetException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private MeetException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode.getMsg(), throwable);
        this.code = errorCode.getCode();
        this.msg = throwable.getMessage();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public static MeetException meet(ErrorCode errorCode) {
        return new MeetException(errorCode);
    }

    public static MeetException meet(ErrorCode errorCode, Object data) {
        return new MeetException(errorCode, data);
    }

    public static MeetException meet(ErrorCode errorCode, String msg) {
        return new MeetException(errorCode, msg);
    }

    public static MeetException meet(ErrorCode errorCode, Throwable throwable) {
        return new MeetException(errorCode, throwable);
    }

    public static MeetException meet(String msg) {
        return new MeetException(CommonErrorCode.ERR_5000, msg);
    }
}
