package org.feather.xd.service;

import org.feather.xd.enums.SendCodeEnum;
import org.feather.xd.util.JsonData;

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

    JsonData sendCode(SendCodeEnum sendCodeEnum, String to);


}
