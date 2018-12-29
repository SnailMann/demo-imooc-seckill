package com.snailmann.seckill.redis.template.key;

import com.snailmann.seckill.redis.template.base.BaseKeyPrefix;

/**
 * 订单放入缓存的key前缀信息
 * @author liwenjie
 */
public class OrderKey extends BaseKeyPrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
