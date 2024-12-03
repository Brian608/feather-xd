package org.feather.xd.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.model.OrderMessage;
import org.feather.xd.service.IProductOrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.mq
 * @className: ProductOrderMQListener
 * @author: feather
 * @description:
 * @since: 2024-12-03 20:26
 * @version: 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
@RabbitListener(queues = "${mqconfig.order_close_queue}")
public class ProductOrderMQListener {

    private final IProductOrderService productOrderService;

    /**
     * 消息重复消费，幂等性保证
     * 开发情况下如何保证安全
     * description:
     * @param orderMessage
     * @param message
     * @param channel
     * @return
     * @author: feather
     * @since: 2024-12-03 20:37
     **/
    @RabbitHandler
    public  void  closeProductOrder(OrderMessage orderMessage, Message message, Channel channel) throws IOException {
        log.info("监听到消息:[{}]",orderMessage);
        long msgTag = message.getMessageProperties().getDeliveryTag();

        try {
            Boolean flag = productOrderService.closeProductOrder(orderMessage);
            if (flag){
                channel.basicAck(msgTag,false);
            }else {
                channel.basicReject(msgTag,true);
            }
        }catch (Exception e){
            log.error("定时关单异常，[{}],[{}]",e,orderMessage);
            channel.basicReject(msgTag,true);
        }


    }


}
