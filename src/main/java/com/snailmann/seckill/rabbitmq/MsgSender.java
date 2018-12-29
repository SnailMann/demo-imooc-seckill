package com.snailmann.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.snailmann.seckill.config.RabbitConfig;
import com.snailmann.seckill.rabbitmq.bean.SeckillMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgSender {

    @Autowired
    AmqpTemplate amqpTemplate;


    public void sendSeckillMsg(SeckillMessage seckillMessage) {
        log.info("send msg : {}", JSON.toJSONString(seckillMessage));
        amqpTemplate.convertAndSend(RabbitConfig.SECKILL_QUEUE, JSON.toJSONString(seckillMessage));
    }
}
