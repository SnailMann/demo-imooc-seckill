package com.snailmann.seckill.entity;

import lombok.Data;

import java.util.Date;

/**
 * 秒杀商品实体
 */
@Data
public class BusinessGoods {
	private Long id;
	private Long goodsId;
    private Double seckillPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
}
