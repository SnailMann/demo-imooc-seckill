package com.snailmann.seckill.redis.template.impl;

import com.snailmann.seckill.redis.template.abs.AbstractKeyPrefix;

public class OrderKey extends AbstractKeyPrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
