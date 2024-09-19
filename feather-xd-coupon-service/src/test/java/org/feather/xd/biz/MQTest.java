package org.feather.xd.biz;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.CouponApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.biz
 * @className: MQTest
 * @author: feather
 * @description:
 * @since: 2024-09-19 9:35
 * @version: 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CouponApplication.class)
public class MQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSendDelayMsg(){

        rabbitTemplate.convertAndSend("coupon.event.exchange","coupon.release.delay.routing.key","this is coupon record lock msg测试测试");

    }


}
