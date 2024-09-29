package org.feather.xd.mq;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.model.CouponRecordMessage;
import org.feather.xd.service.ICouponRecordService;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.mq
 * @className: CouponMQListener
 * @author: feather
 * @description:
 * @since: 2024-09-19 11:24
 * @version: 1.0
 */
@RequiredArgsConstructor
@Component
@Slf4j
@RabbitListener(queues = "${mqconfig.coupon_release_queue}")
public class CouponMQListener {

    private final RedissonClient redissonClient;

    private final ICouponRecordService couponRecordService;

    /**
     * description:
     * 重复消费 幂等性
     * 消费失败，重新入队后最大重试次数
     * 如果消费失败，不重新入队，可以记录日志，记录到数据库人工排查
     * @param recordMessage
     * @param message
     * @param channel
     * @return
     * @author: feather
     * @since: 2024-09-29 15:49
     **/
    @RabbitListener
    public  void  releaseCouponRecord(CouponRecordMessage recordMessage,Message message,Channel channel){

        //防止同个解锁任务并发进入；如果是串行消费不用加锁；加锁有利也有弊，看项目业务逻辑而定
        //Lock lock = redissonClient.getLock("lock:coupon_record_release:"+recordMessage.getTaskId());
        //lock.lock();
        log.info("监听到消息:消息内容:[{}]",recordMessage);
        long msgTag = message.getMessageProperties().getDeliveryTag();
        boolean flag =  couponRecordService.releaseCouponRecord(recordMessage);
       try {
           if (flag){
               //确认消息消费成功
                channel.basicAck(msgTag,false);
           }else {
               log.info("释放优惠券失败[{}]",recordMessage);
               channel.basicReject(msgTag,true);
           }
       }catch (Exception e ){
          log.error("释放优惠券记录异常，[{}],[{}]",e,recordMessage);
          try {
              channel.basicReject(msgTag,true);
          } catch (IOException ex) {
              throw new RuntimeException(ex);
          }
       }

    }



}
