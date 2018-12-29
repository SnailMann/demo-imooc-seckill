package com.snailmann.seckill.redis.template.impl;

import com.snailmann.seckill.redis.template.base.BaseKeyPrefix;

public class Goodskey extends BaseKeyPrefix {

    public Goodskey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static Goodskey getGoodsList = new Goodskey(60,"goods-list-");
    public static Goodskey getGoodsDetail = new Goodskey(60,"goods-detail-");
    public static Goodskey getSeckillGoodsStockCount = new Goodskey(60,"goods-count-");

}
