package org.feather.xd.exception;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.util.JsonResult;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.exception
 * @className: ExceptionHandler
 * @author: feather
 * @description:
 * @since: 2024-08-09 16:41
 * @version: 1.0
 */
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public JsonResult<Object> handle(Exception e){
        log.error("[系统异常: {}]",e.getMessage());
        return JsonResult.buildError("全局异常，未知错误");
    }


    /**
     *  HTTP请求方法类型错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public JsonResult<Object> handle(HttpRequestMethodNotSupportedException e) {
        log.error("[客户端HTTP请求方法错误]", e);
        return JsonResult.buildError(BizCodeEnum.CLIENT_HTTP_METHOD_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult<Object> handle(MethodArgumentNotValidException e) {
        log.error("[客户端请求体参数校验不通过]", e);
        String errorMsg = this.handle(e.getBindingResult().getFieldErrors());
        return JsonResult.buildError(BizCodeEnum.CLIENT_REQUEST_BODY_CHECK_ERROR.getCode(),errorMsg);
    }
    private String handle(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for (FieldError obj : fieldErrors) {
            sb.append(obj.getField());
            sb.append("=[");
            sb.append(obj.getDefaultMessage());
            sb.append("]  ");
        }
        return sb.toString();
    }

    /**
     *  客户端请求体JSON格式错误或字段类型不匹配
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public JsonResult<Object> handle(HttpMessageNotReadableException e) {
        log.error("[客户端请求体JSON格式错误或字段类型不匹配]", e);
        return JsonResult.buildError(BizCodeEnum.CLIENT_REQUEST_BODY_FORMAT_ERROR);
    }

    /**
     *  客户端URL中的参数类型错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public JsonResult<Object> handle(BindException e) {
        log.error("[客户端URL中的参数类型错误]", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMsg = null;
        if (fieldError != null) {
            errorMsg = fieldError.getDefaultMessage();
            if (errorMsg != null && errorMsg.contains("java.lang.NumberFormatException")) {
                errorMsg = fieldError.getField() + "参数类型错误";
            }
        }
        if (errorMsg != null && !errorMsg.isEmpty()) {
            return JsonResult.buildError(BizCodeEnum.CLIENT_PATH_VARIABLE_ERROR.getCode(), errorMsg);
        }
        return JsonResult.buildError(BizCodeEnum.CLIENT_PATH_VARIABLE_ERROR);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public JsonResult<Object> handle(ConstraintViolationException e) {
        log.error("[客户端请求参数校验不通过]", e);
        Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator();
        String errorMsg = null;
        if (it.hasNext()) {
            errorMsg = it.next().getMessageTemplate();
        }
        if (errorMsg != null && !errorMsg.isEmpty()) {
            return JsonResult.buildError(BizCodeEnum.CLIENT_REQUEST_PARAM_CHECK_ERROR.getCode(), errorMsg);
        }
        return JsonResult.buildError(BizCodeEnum.CLIENT_REQUEST_PARAM_CHECK_ERROR);
    }

    /**
     *  客户端请求缺少必填的参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public JsonResult<Object> handle(MissingServletRequestParameterException e) {
        log.error("[客户端请求缺少必填的参数]", e);
        String errorMsg = null;
        String parameterName = e.getParameterName();
        if (!parameterName.isEmpty()) {
            errorMsg = parameterName + "不能为空";
        }
        if (errorMsg != null) {
            return JsonResult.buildError(BizCodeEnum.CLIENT_REQUEST_PARAM_REQUIRED_ERROR.getCode(), errorMsg);
        }
        return JsonResult.buildError(BizCodeEnum.CLIENT_REQUEST_PARAM_REQUIRED_ERROR);
    }
    /**
     *  业务方法参数检查不通过
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JsonResult<Object> handle(IllegalArgumentException e) {
        log.error("[业务方法参数检查不通过]", e);
        return JsonResult.buildError(BizCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
    }
    /**
     * 系统自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public JsonResult<Object> handle(BizException e) {
        log.error("[ 业务异常 ]", e);
        return JsonResult.buildError(e.getCode(), e.getMsg());
    }

}
