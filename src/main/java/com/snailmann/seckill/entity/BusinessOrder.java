package com.snailmann.seckill.entity;

import lombok.Data;

/**
 * 秒杀订单实体
 */
@Data
public class BusinessOrder {
	private Long id;
	private Long userId;
	private Long  orderId;
	private Long goodsId;

}
