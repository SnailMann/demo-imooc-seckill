package com.snailmann.seckill.constant;

import lombok.Getter;

/**
 * 订单状态枚举类
 *
 * @author liwenjie
 */

public enum OrderEnum {

    /**
     * 新建未支付订单
     */
    NEW(0),
    /**
     * 已支付订单
     */
    AREADY_PAYED(1),
    /**
     * 已发货订单
     */
    AREADY_DELIVERED(2),
    /**
     * 已收货订单
     */
    AREADY_RECEIVED(3),
    /**
     * 已退款订单
     */
    AREADY_CANCEL(4),
    /**
     * 已完成订单
     */
    DONE(5);

    @Getter
    private int status;

    OrderEnum(int status) {
        this.status = status;
    }

}
