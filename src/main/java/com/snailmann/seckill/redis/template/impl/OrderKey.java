package com.snailmann.seckill.redis.template.impl;

import com.snailmann.seckill.redis.template.base.AbstractKeyPrefix;

/**
 * 订单放入缓存的key前缀信息
 * @author liwenjie
 */
public class OrderKey extends AbstractKeyPrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
