package com.snailmann.seckill.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.snailmann.seckill.config.RabbitConfig;
import com.snailmann.seckill.constant.CodeMsg;
import com.snailmann.seckill.entity.BusinessOrder;
import com.snailmann.seckill.entity.dto.GoodsDto;
import com.snailmann.seckill.rabbitmq.bean.SeckillMessage;
import com.snailmann.seckill.service.GoodsService;
import com.snailmann.seckill.service.OrderService;
import com.snailmann.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgReceiver {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;

    @RabbitListener(queues = RabbitConfig.SECKILL_QUEUE)
    public void handleMsg(Message message) {
        String result = new String(message.getBody());
        log.info("receive msg : {}", result);
        SeckillMessage seckillMessage = JSON.parseObject(result,SeckillMessage.class);
        GoodsDto goodsDto = goodsService.getGoodsDto("" + seckillMessage.getGoodsId());
        //判断是否有库存，MySQL层面
        int stockCount = goodsDto.getStockCount();
        if (stockCount < 0) {
            return;
        }
        //判断是否已经秒杀到
        BusinessOrder businessOrder = orderService.getSeckillOrder("" + seckillMessage.getGoodsId(), seckillMessage.getUser().getId());
        if (businessOrder != null) {
            return;
        }

        //减库存，下订单，写入秒杀订单
        seckillService.doSeckill(seckillMessage.getUser(), goodsDto);
    }
}
