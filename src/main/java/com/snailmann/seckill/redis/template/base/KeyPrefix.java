package com.snailmann.seckill.redis.template.base;

/**
 * 通用缓存key顶层接口，模板模式
 */
public interface KeyPrefix {

    int expireSeconds();
    String getPrefix();


}
