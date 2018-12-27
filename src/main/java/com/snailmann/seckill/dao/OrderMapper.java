package com.snailmann.seckill.dao;

import com.snailmann.seckill.entity.BusinessOrder;
import com.snailmann.seckill.entity.Order;
import org.apache.ibatis.annotations.*;

/**
 * 订单Dao层
 * @author liwenjie
 */
@Mapper
public interface OrderMapper {

    /**
     * 根据商品id和用户id获取秒杀订单
     * @param goodsId
     * @param userId
     */
    @Select("select * from seckill_business_order where user_id =#{userId} and goods_id =#{goodsId}")
    BusinessOrder getSeckillOrder(@Param("goodsId") String goodsId, @Param("userId") Long userId);

    /**
     * 插入新订单信息
     * @SelectKey() 是为了返回订单号long型,即返回自增id
     * @param order
     */
    @Insert("insert into seckill_order(user_id, goods_id, delivery_addr_id, goods_name, goods_count, goods_price, order_channel, status, create_date) " +
            "values (#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(Order order);

    /**
     * 插入秒杀订单信息
     * @param businessOrder
     */
    @Insert("insert into seckill_business_order(user_id, order_id, goods_id) values (#{userId},#{orderId},#{goodsId})")
    void insertBusinessOrder(BusinessOrder businessOrder);
}
