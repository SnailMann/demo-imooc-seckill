package com.snailmann.seckill.entity.dto;

import com.snailmann.seckill.entity.Goods;
import lombok.Data;

import java.util.Date;

/**
 * 商品完整信息实体
 */
@Data
public class GoodsDto extends Goods {

    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
