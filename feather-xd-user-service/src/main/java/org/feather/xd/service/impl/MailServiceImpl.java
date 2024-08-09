package org.feather.xd.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service.impl
 * @className: MailServiceImpl
 * @author: feather
 * @description:
 * @since: 2024-08-09 21:24
 * @version: 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class MailServiceImpl  implements IMailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content) {

        //创建一个邮箱消息对象
        SimpleMailMessage message = new SimpleMailMessage();

        //配置邮箱发送人
        message.setFrom(from);

        //邮件的收件人
        message.setTo(to);

        //邮件的主题
        message.setSubject(subject);

        //邮件的内容
        message.setText(content);

        mailSender.send(message);

        log.info("邮件发送成功:[{}]",message);
    }
}
