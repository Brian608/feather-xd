package org.feather.xd.service;

import org.feather.xd.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.request.UserRegisterRequest;
import org.feather.xd.util.JsonResult;

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
}
