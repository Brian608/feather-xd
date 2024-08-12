package org.feather.xd.service;

import org.feather.xd.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.request.UserLoginRequest;
import org.feather.xd.request.UserRegisterRequest;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.LoginInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
public interface IUserService extends IService<UserDO> {

    /**
     * description: 用户注册
     * @param registerRequest
     * @return {@link JsonResult}
     * @author: feather
     * @since: 2024-08-11 13:28
     **/
    Boolean register(UserRegisterRequest registerRequest);

    /**
     * description: 登录
     *  * 1、根据Mail去找有没这记录
     *  * 2、有的话，则用秘钥+用户传递的明文密码，进行加密，再和数据库的密文进行匹配
     * @param userLoginRequest
     * @return {@link LoginInfo}
     * @author: feather
     * @since: 2024-08-12 10:02
     **/
    LoginInfo login(UserLoginRequest userLoginRequest);
}
