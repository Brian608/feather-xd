package org.feather.xd.mq;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.model.ProductMessage;
import org.feather.xd.service.IProductService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.mq
 * @className: ProductStockMQListener
 * @author: feather
 * @description:
 * @since: 2024-10-22 15:45
 * @version: 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
@RabbitListener(queues = "${mqconfig.stock_release_queue}")
public class ProductStockMQListener {


    private   final  IProductService productService;

    @RabbitHandler
    public void releaseProductStock(ProductMessage productMessage, Message message, Channel channel) throws IOException {

        log.info("监听到消息：releaseProductStock消息内容：{}", productMessage);
        long msgTag = message.getMessageProperties().getDeliveryTag();

        boolean flag = productService.releaseProductStock(productMessage);

        try {
            if (flag) {
                //确认消息消费成功
                channel.basicAck(msgTag, false);
            }else {
                channel.basicReject(msgTag,true);
                log.error("释放商品库存失败 flag=false,{}",productMessage);
            }

        } catch (IOException e) {
            log.error("释放商品库存异常:{},msg:{}",e,productMessage);
            channel.basicReject(msgTag,true);
        }



    }



}
