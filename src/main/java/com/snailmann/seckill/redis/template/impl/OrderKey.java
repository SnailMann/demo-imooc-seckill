package com.snailmann.seckill.redis.template.impl;

import com.snailmann.seckill.redis.template.base.AbstractKeyPrefix;

public class OrderKey extends AbstractKeyPrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
