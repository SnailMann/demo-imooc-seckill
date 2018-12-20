package com.snailmann.seckill.redis.template.impl;

import com.snailmann.seckill.entity.po.User;
import com.snailmann.seckill.redis.template.base.AbstractKeyPrefix;

public class UserKey extends AbstractKeyPrefix {

    /**
     * test
     */
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
    public static UserKey token = getUserKey(30,"token");


    /**
     * Key永不过期
     * @param prefix
     */
    public UserKey(String prefix) {
        super(0, prefix);
    }

    /**
     * 有过期时间
     * @param expireSeconds
     * @param prefix
     */
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 简单工厂方法
     * @param expireSeconds
     * @param prefix
     * @return
     */
    public static UserKey getUserKey(int expireSeconds, String prefix){
        return new UserKey(expireSeconds,prefix);
    }


}
