package com.snailmann.seckill.redis.template.base;

import com.snailmann.seckill.redis.template.KeyPrefix;

/**
 *  模板模式抽象类
 */
public abstract class AbstractKeyPrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    /**
     * key永远不过期的构造函数
     * @param prefix
     */
    public AbstractKeyPrefix(String prefix) {
        this.expireSeconds = 0;
        this.prefix = prefix;
    }

    public AbstractKeyPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 默认为0，代表永远不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return 0;
    }

    /**
     * 具体子类的类名与prefix进行拼接作为Key
     * @return
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix + ":";
    }
}
