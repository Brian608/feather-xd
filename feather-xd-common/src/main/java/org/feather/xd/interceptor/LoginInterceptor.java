package org.feather.xd.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.model.LoginUser;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.util.JWTUtil;
import org.feather.xd.util.JsonResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.interceptor
 * @className: LoginInterceptor
 * @author: feather
 * @description:
 * @since: 2024-08-12 16:49
 * @version: 1.0
 */
@Slf4j
public class LoginInterceptor  implements HandlerInterceptor {

    public static final ThreadLocal<LoginUser> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器拦截请求");
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)){
            Claims claims = JWTUtil.checkJWT(token);
            if (claims==null){
                //未登录
                CommonUtil.sendJsonMessage(response, JsonResult.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }
            long userId = Long.parseLong(claims.get("id").toString());
            String headImg = (String)claims.get("head_img");
            String name = (String)claims.get("name");
            String mail = (String)claims.get("mail");


            LoginUser loginUser = LoginUser
                    .builder()
                    .headImg(headImg)
                    .name(name)
                    .id(userId)
                    .mail(mail).build();
            LOGIN_USER_THREAD_LOCAL.set(loginUser);
            return true;
        }
        CommonUtil.sendJsonMessage(response, JsonResult.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGIN_USER_THREAD_LOCAL.remove();
    }
}
