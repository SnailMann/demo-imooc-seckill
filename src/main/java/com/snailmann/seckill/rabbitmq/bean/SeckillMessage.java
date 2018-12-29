package com.snailmann.seckill.rabbitmq.bean;

import com.snailmann.seckill.entity.User;
import lombok.Data;

@Data
public class SeckillMessage {

    private User user;
    private Long goodsId;


}
