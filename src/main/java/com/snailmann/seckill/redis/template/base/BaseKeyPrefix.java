package com.snailmann.seckill.redis.template.base;

/**
 *  键前缀抽象类（模板模式）
 * @author liwenjie
 */
public abstract class BaseKeyPrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    /**
     * key永远不过期的构造函数
     * @param prefix
     */
    public BaseKeyPrefix(String prefix) {
        this.expireSeconds = 0;
        this.prefix = prefix;
    }

    public BaseKeyPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 默认为0，代表永远不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return this.expireSeconds;
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
