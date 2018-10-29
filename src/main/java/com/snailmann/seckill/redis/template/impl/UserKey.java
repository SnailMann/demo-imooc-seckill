package com.snailmann.seckill.redis.template.impl;

import com.snailmann.seckill.redis.template.abs.AbstractKeyPrefix;

public class UserKey extends AbstractKeyPrefix {


    /**
     * Key永不过期
     * @param prefix
     */
    public UserKey(String prefix) {
        super(0, prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
