package org.feather.xd.exception;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.util.JsonResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.exception
 * @className: SentinelBlockHandler
 * @author: feather
 * @description: 自定义流控协议异常
 * @since: 2025-02-27 20:48
 * @version: 1.0
 */
@Component
public class SentinelBlockHandler  implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        JsonResult jsonResult=null;
        if (e instanceof FlowException){
            jsonResult=JsonResult.buildResult(BizCodeEnum.CONTROL_FLOW);
        } else if (e instanceof DegradeException) {
            jsonResult=JsonResult.buildResult(BizCodeEnum.CONTROL_DEGRADE);
        }else if(e instanceof AuthorityException){
            jsonResult = JsonResult.buildResult(BizCodeEnum.CONTROL_AUTH);
        }
        httpServletResponse.setStatus(200);

        CommonUtil.sendJsonMessage(httpServletResponse,jsonResult);
    }
}
