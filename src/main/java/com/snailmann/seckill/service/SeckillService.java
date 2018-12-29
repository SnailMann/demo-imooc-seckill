package com.snailmann.seckill.service;

import com.snailmann.seckill.entity.Goods;
import com.snailmann.seckill.entity.Order;
import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.entity.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀业务层
 * @author liwenjie
 */
@Service
public class SeckillService {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    /**
     * 执行秒杀操作(注意，这是一个原子操作，记得加事物)
     * @param user
     * @param goodsDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Order doSeckill(User user, GoodsDto goodsDto){
        //减库存
        boolean isSuccess = goodsService.reduceStock(goodsDto);

        //下订单,写入秒杀订单
        if (isSuccess){
            //如果减库存成功，才能下订单
            Order order = orderService.createOrder(user,goodsDto);
            return order;
        } else {
            //如果减库存失败，则返回null,交给调用方判断
            return null;
        }

    }
}
