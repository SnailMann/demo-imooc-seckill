package com.snailmann.seckill.entity;

import lombok.Data;

/**
 * 商品信息实体
 */
@Data
public class Goods {
	private Long id;
	private String goodsName;
	private String goodsTitle;
	private String goodsImg;
	private String goodsDetail;
	private Double goodsPrice;
	private Integer goodsStock;
}
