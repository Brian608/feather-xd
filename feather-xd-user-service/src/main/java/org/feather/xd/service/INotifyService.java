package org.feather.xd.service;

import org.feather.xd.enums.SendCodeEnum;
import org.feather.xd.util.JsonResult;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service
 * @className: INotifyService
 * @author: feather
 * @description:
 * @since: 2024-08-09 21:43
 * @version: 1.0
 */
public interface INotifyService {

    /**
     * description: 发送验证码
     * @param sendCodeEnum
     * @param to
     * @return {@link JsonResult}
     * @author: feather
     * @since: 2024-08-11 16:42
     **/
    Boolean sendCode(SendCodeEnum sendCodeEnum, String to);

    /**
     * description: 验证验证码是否一致
     * @param sendCodeEnum
     * @param to
     * @param code
     * @return {@link boolean}
     * @author: feather
     * @since: 2024-08-11 16:43
     **/
    boolean checkCode(SendCodeEnum sendCodeEnum,String to,String code);


}
