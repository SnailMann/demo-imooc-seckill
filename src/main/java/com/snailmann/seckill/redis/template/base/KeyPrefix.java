package com.snailmann.seckill.redis.template.base;

/**
 * 通用缓存key顶层接口
 * @author liwenjie
 */
public interface KeyPrefix {

    /**
     * 返回过期时间
     * @return 过期时间，单位s
     */
    int expireSeconds();

    /**
     * 返回key的前缀
     * @return
     */
    String getPrefix();


}
