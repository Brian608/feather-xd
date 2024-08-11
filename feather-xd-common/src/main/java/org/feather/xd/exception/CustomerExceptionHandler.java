package org.feather.xd.exception;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.util.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.exception
 * @className: ExceptionHandler
 * @author: feather
 * @description:
 * @since: 2024-08-09 16:41
 * @version: 1.0
 */
@ControllerAdvice
@Slf4j
public class CustomerExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult handle(Exception e){

        //是不是自定义异常
        if(e instanceof BizException){
            BizException bizException = (BizException) e;
            log.error("[业务异常: {}]",e);
            return JsonResult.buildCodeAndMsg(bizException.getCode(),bizException.getMsg());

        }else{
            log.error("[系统异常: {}]",e);
            return JsonResult.buildError("全局异常，未知错误");
        }

    }

}
