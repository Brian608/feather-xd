package org.feather.xd.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.util.CommonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.controller
 * @className: NotifyController
 * @author: feather
 * @description: 图形验证码
 * @since: 2024-08-09 20:19
 * @version: 1.0
 */
@RequiredArgsConstructor
@Api(tags = "通知模块")
@RestController
@RequestMapping("/api/user/v1")
@Slf4j
public class NotifyController {

    private  final  Producer captchaProducer;

    private final StringRedisTemplate redisTemplate;


    /**
     * 图形验证码有效期10分钟
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String captchaText = captchaProducer.createText();
        log.info("图形验证码:{}",captchaText);
        //存储
        redisTemplate.opsForValue().set(getCaptchaKey(request),captchaText,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("获取图形验证码异常:[{}]",e);
        }

    }

    /**
     * 获取缓存的key
     * @param request
     * @return
     */
    private String getCaptchaKey(HttpServletRequest request){

        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");

        String key = "user-service:captcha:"+ CommonUtil.MD5(ip+userAgent);

        log.info("ip={}",ip);
        log.info("userAgent={}",userAgent);
        log.info("key={}",key);

        return key;

    }


}
