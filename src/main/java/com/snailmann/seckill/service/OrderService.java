package com.snailmann.seckill.service;

import com.snailmann.seckill.constant.OrderEnum;
import com.snailmann.seckill.dao.OrderMapper;
import com.snailmann.seckill.entity.BusinessOrder;
import com.snailmann.seckill.entity.Order;
import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.entity.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 订单业务层
 * @author liwenjie
 */
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    /**
     * 根据用户id和商品id获得对应的秒杀订单信息
     * @param goodsId
     * @param userId
     * @return
     */
    public BusinessOrder getSeckillOrder(String goodsId, Long userId) {
        return orderMapper.getSeckillOrder(goodsId,userId);
    }

    /**
     * 下订单,谁（user）下了什么(goodsDto)订单
     * 因为涉及订单信息和秒杀订单信息，这是一个原子操作，所以记得加上事物
     * @param user
     * @param goodsDto
     */
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(User user, GoodsDto goodsDto) {

        //1. 订单信息
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsCount(1);
        order.setGoodsId(goodsDto.getId());
        order.setGoodsName(goodsDto.getGoodsName());
        //不是原价，是秒杀价格
        order.setGoodsPrice(goodsDto.getSeckillPrice());
        order.setOrderChannel(1);
        //新建未付款订单
        order.setStatus(OrderEnum.NEW.getStatus());
        order.setUserId(user.getId());
        long orderId = orderMapper.insert(order);

        //2. 秒杀订单信息
        BusinessOrder businessOrder = new BusinessOrder();
        businessOrder.setGoodsId(goodsDto.getId());
        businessOrder.setOrderId(orderId);
        businessOrder.setUserId(user.getId());
        orderMapper.insertBusinessOrder(businessOrder);

        return order;
    }
}
