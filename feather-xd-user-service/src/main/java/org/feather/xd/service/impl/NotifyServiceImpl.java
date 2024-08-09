package org.feather.xd.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.component.IMailService;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.SendCodeEnum;
import org.feather.xd.service.INotifyService;
import org.feather.xd.util.CheckUtil;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.util.JsonData;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service.impl
 * @className: NotifyServiceImpl
 * @author: feather
 * @description:
 * @since: 2024-08-09 21:45
 * @version: 1.0
 */
@AllArgsConstructor
@Slf4j
@Service
public class NotifyServiceImpl implements INotifyService {

    private IMailService mailService;

    private final StringRedisTemplate redisTemplate;

    /**
     * 验证码的标题
     */
    private static final String SUBJECT= "feather-xd验证码";

    /**
     * 验证码的内容
     */
    private static final String CONTENT= "您的验证码是%s,有效时间是10分钟,打死也不要告诉任何人";

    /**
     * 验证码10分钟有效
     */
    private static final int CODE_EXPIRED = 60 * 1000 * 10;
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {

        if(CheckUtil.isEmail(to)){
            String code = CommonUtil.getRandomCode(6);
            //邮箱验证码
            mailService.sendMail(to,SUBJECT,String.format(CONTENT,code));

            return JsonData.buildSuccess();

        } else if(CheckUtil.isPhone(to)){
            //短信验证码

        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

}
