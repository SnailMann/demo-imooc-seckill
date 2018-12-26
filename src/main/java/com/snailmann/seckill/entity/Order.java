package com.snailmann.seckill.entity;

import lombok.Data;

import java.util.Date;

/**
 * 订单信息实体
 */
@Data
public class Order {
	private Long id;
	private Long userId;
	private Long goodsId;
	private Long  deliveryAddrId;
	private String goodsName;
	private Integer goodsCount;
	private Double goodsPrice;
	private Integer orderChannel;
	private Integer status;
	private Date createDate;
	private Date payDate;
}
