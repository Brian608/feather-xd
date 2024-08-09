package org.feather.xd.bz;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.UserApplication;
import org.feather.xd.component.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.bz
 * @className: MailTest
 * @author: feather
 * @description:
 * @since: 2024-08-09 21:29
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MailTest {


    @Autowired
    private  IMailService mailService;

    @Test
    public  void testSendMail(){
        mailService.sendMail("826813443@qq.com","测试主题","测试邮件内容");
    }
}
