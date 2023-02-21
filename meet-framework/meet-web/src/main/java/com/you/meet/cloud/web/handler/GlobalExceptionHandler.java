package com.you.meet.cloud.web.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.you.meet.cloud.common.exception.CommonErrorCode;
import com.you.meet.cloud.common.exception.MeetException;
import com.you.meet.cloud.common.pojo.JSONResponse;
import java.util.Iterator;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public JSONResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Iterator<ObjectError> errorIterator = e.getBindingResult().getAllErrors().iterator();
        JSONResponse jsonResponse = JSONResponse.builder().code(CommonErrorCode.P_BAD_PARAMETER.getCode()).data(e.getMessage()).build();
        if (errorIterator.hasNext()) {
            ObjectError error = errorIterator.next();
            jsonResponse.setMsg(error.getDefaultMessage());
        }
        return jsonResponse;
    }

    @ExceptionHandler(value = BlockException.class)
    public JSONResponse handleBlockException(BlockException blockException) {
        return JSONResponse.builder().code(CommonErrorCode.S_SYSTEM_BUSY.getCode()).msg("请求被拦截,拦截类型为 " + blockException.getClass().getSimpleName()).build();
    }

    @ExceptionHandler({MeetException.class})
    public JSONResponse handleMeetException(MeetException e) {
        return JSONResponse.builder().code(e.getCode()).data(e.getData()).msg(e.getMsg()).build();
    }

    @ExceptionHandler({Exception.class})
    public JSONResponse handleUnknownException(Exception e) {
        log.error("未知异常:", e);
        return JSONResponse.builder().code(CommonErrorCode.S_SYSTEM_BUSY.getCode()).msg("接口异常，请联系管理员！").build();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public JSONResponse handleConstraintViolationException(ConstraintViolationException e) {
        Optional<ConstraintViolation<?>> constraintViolation = e.getConstraintViolations().stream().findFirst();
        String messageTemplate = "";
        if (constraintViolation.isPresent()) {
            messageTemplate = constraintViolation.get().getMessageTemplate();
        }
        return JSONResponse.builder().code(CommonErrorCode.P_PARAM_CHECK_ERROR.getCode()).msg("参数校验未通过!").data(messageTemplate).build();
    }

}
