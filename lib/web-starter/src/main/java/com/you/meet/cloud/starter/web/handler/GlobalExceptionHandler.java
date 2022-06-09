package com.you.meet.cloud.starter.web.handler;

import com.you.meet.cloud.lib.api.common.exception.CommonErrorCode;
import com.you.meet.cloud.lib.api.common.exception.MeetException;
import com.you.meet.cloud.lib.api.common.pojo.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Optional;

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
    public JsonResponse handleUnProcessableException(MethodArgumentNotValidException e) {
        Iterator<ObjectError> errorIterator = e.getBindingResult().getAllErrors().iterator();
        JsonResponse jsonResponse = JsonResponse.builder().code(CommonErrorCode.ERR_5002.getCode()).data(e.getMessage()).build();
        if (errorIterator.hasNext()) {
            ObjectError error = errorIterator.next();
            jsonResponse.setMsg(error.getDefaultMessage());
        }
        return jsonResponse;
    }

    @ExceptionHandler({MeetException.class})
    @ResponseStatus(HttpStatus.OK)
    public JsonResponse handleUnProcessableException(MeetException e) {
        return JsonResponse.builder().code(e.getCode()).data(e.getData()).msg(e.getMsg()).build();
    }

    @ExceptionHandler({Exception.class})
    public JsonResponse handleUnHandlerException(Exception e, HttpServletResponse response) {
        return JsonResponse.builder().code(CommonErrorCode.ERR_5000.getCode()).msg("接口异常，请联系管理员！").build();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public JsonResponse handleValidException(ConstraintViolationException e, HttpServletResponse response) {
        Optional<ConstraintViolation<?>> constraintViolation = e.getConstraintViolations().stream().findFirst();
        String messageTemplate = "";
        if (constraintViolation.isPresent()) {
            messageTemplate = constraintViolation.get().getMessageTemplate();
        }
        return JsonResponse.builder().code(CommonErrorCode.ERR_5002.getCode()).msg("参数校验未通过!").data(messageTemplate).build();
    }


}
