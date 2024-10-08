package org.feather.xd.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.component.IMailService;
import org.feather.xd.constant.CacheKey;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.SendCodeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.service.INotifyService;
import org.feather.xd.util.CheckUtil;
import org.feather.xd.util.CommonUtil;
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

    /**
     * 前置：判断是否重复发送
     *
     * 1、存储验证码到缓存
     *
     * 2、发送邮箱验证码
     *
     * 后置：存储发送记录
     *
     * @param sendCodeEnum
     * @param to
     * @return
     */
    @Override
    public Boolean sendCode(SendCodeEnum sendCodeEnum, String to) {

        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),to);

        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        //如果不为空，则判断60s内重复发送
        if (StringUtils.isNotBlank(cacheValue)){
            long  ttl= Long.parseLong(cacheValue.split("_")[1]);
            //当前时间戳-验证码发送时间戳，如果小于60秒，则不给重复发送
            if (CommonUtil.getCurrentTimestamp() - ttl < 1000 * 60) {
                log.info("重复发送,时间间隔:[{}] 秒", (CommonUtil.getCurrentTimestamp() - ttl)/1000);
                throw new BizException(BizCodeEnum.CODE_LIMITED);
            }

        }
        //拼接验证码 2322_324243232424324
        String code = CommonUtil.getRandomCode(6);

        String value=code+"_"+CommonUtil.getCurrentTimestamp();

        redisTemplate.opsForValue().set(cacheKey,value,CODE_EXPIRED,TimeUnit.MILLISECONDS);

        if(CheckUtil.isEmail(to)){

            //邮箱验证码
            mailService.sendMail(to,SUBJECT,String.format(CONTENT,code));

            return true;

        } else if(CheckUtil.isPhone(to)){
            //短信验证码

        }

        return false;
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)){
            if (code.equals(cacheValue.split("_")[0])){
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
