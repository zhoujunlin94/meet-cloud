package com.you.meet.cloud.web.handler;

import com.you.meet.cloud.common.exception.CommonErrorCode;
import com.you.meet.cloud.common.exception.MeetException;
import com.you.meet.cloud.common.pojo.JSONResponse;
import java.util.Iterator;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author zhoujl
 * @Date 2020/5/5 14:34
 * @Description
 **/
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public JSONResponse handleUnProcessableException(MethodArgumentNotValidException e) {
        Iterator<ObjectError> errorIterator = e.getBindingResult().getAllErrors().iterator();
        JSONResponse jsonResponse = JSONResponse.builder().code(CommonErrorCode.P_BAD_PARAMETER.getCode()).data(e.getMessage()).build();
        if (errorIterator.hasNext()) {
            ObjectError error = errorIterator.next();
            jsonResponse.setMsg(error.getDefaultMessage());
        }
        return jsonResponse;
    }

    @ExceptionHandler({MeetException.class})
    @ResponseStatus(HttpStatus.OK)
    public JSONResponse handleUnProcessableException(MeetException e) {
        return JSONResponse.builder().code(e.getCode()).data(e.getData()).msg(e.getMsg()).build();
    }

    /*@ExceptionHandler({Exception.class})
    public JSONResponse handleUnHandlerException(Exception e, HttpServletResponse response) {
        return JSONResponse.builder().code(CommonErrorCode.S_SYSTEM_BUSY.getCode()).msg("接口异常，请联系管理员！").build();
    }*/

    @ExceptionHandler({ConstraintViolationException.class})
    public JSONResponse handleValidException(ConstraintViolationException e, HttpServletResponse response) {
        Optional<ConstraintViolation<?>> constraintViolation = e.getConstraintViolations().stream().findFirst();
        String messageTemplate = "";
        if (constraintViolation.isPresent()) {
            messageTemplate = constraintViolation.get().getMessageTemplate();
        }
        return JSONResponse.builder().code(CommonErrorCode.P_PARAM_CHECK_ERROR.getCode()).msg("参数校验未通过!").data(messageTemplate).build();
    }


}
