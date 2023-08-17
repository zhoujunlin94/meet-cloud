package com.you.meet.cloud.web.handler;

import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.common.exception.CommonErrorCode;
import com.you.meet.cloud.common.exception.MeetException;
import com.you.meet.cloud.common.pojo.JSONResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Iterator;
import java.util.Optional;

/**
 * @Author zhoujl
 * @Date 2020/5/5 14:34
 * @Description
 **/
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public JSONResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Iterator<ObjectError> errorIterator = e.getBindingResult().getAllErrors().iterator();
        JSONResponse jsonResponse = JSONResponse.builder().code(CommonErrorCode.P_BAD_PARAMETER.getCode()).msg(e.getMessage()).build();
        if (errorIterator.hasNext()) {
            ObjectError error = errorIterator.next();
            jsonResponse.setMsg(error.getDefaultMessage());
        }
        return jsonResponse;
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public JSONResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return JSONResponse.builder().code(CommonErrorCode.P_BAD_PARAMETER.getCode()).msg(e.getMessage()).build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public JSONResponse handle(HttpMessageNotReadableException e) {
        return JSONResponse.builder().code(CommonErrorCode.P_BAD_PARAMETER.getCode()).msg(e.getMessage()).build();
    }

    @ExceptionHandler({MeetException.class})
    public JSONResponse handleMeetException(MeetException e) {
        return JSONResponse.builder().code(e.getCode()).data(e.getData()).msg(e.getMsg()).build();
    }

    @ExceptionHandler({Exception.class})
    public JSONResponse handleUnknownException(Exception e) {
        log.error("未知异常:", e);
        if (e instanceof UndeclaredThrowableException) {
            Throwable undeclaredThrowable = ((UndeclaredThrowableException) e).getUndeclaredThrowable();
            // todo 后续有一些可预期的异常可以instanceof判断生成相应响应
        }
        return JSONResponse.builder().code(CommonErrorCode.S_SYSTEM_BUSY.getCode()).msg("接口异常，请联系管理员！").build();
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public JSONResponse handleConstraintViolationException(ConstraintViolationException e) {
        Optional<ConstraintViolation<?>> constraintViolationOpt = e.getConstraintViolations().stream().findFirst();
        String messageTemplate = StrUtil.EMPTY;
        if (constraintViolationOpt.isPresent()) {
            messageTemplate = constraintViolationOpt.get().getMessageTemplate();
        }
        return JSONResponse.builder().code(CommonErrorCode.P_PARAM_CHECK_ERROR.getCode()).msg("参数校验未通过!").data(messageTemplate).build();
    }

}
