package com.snailmann.seckill.redis.service;

import com.alibaba.fastjson.JSON;
import com.snailmann.seckill.redis.template.base.KeyPrefix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * Redis通用操作
 * @author liwenjie
 */
@Component
public class RedisHandler {

    @Autowired
    JedisPool jedisPool;

    /**
     * 根据key获取value
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        //java7 语法自动close资源
        try (Jedis jedis = jedisPool.getResource()) {
            //prefix + key 拼接
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            if (StringUtils.isBlank(str)) {
                return null;
            }
            return stringToBean(str, clazz);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;

    }


    /**
     * 有前缀和过期时间
     * 根据key插入value
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        try (Jedis jedis = jedisPool.getResource()) {
            //prefix + key
            String readKey = prefix.getPrefix() + key;
            //获得过期时间
            int seconds = prefix.expireSeconds();
            //如果过期时间为0，则代表用于不过期
            if (seconds <= 0) {
                jedis.set(readKey, beanToString(value));
            }
           //如果过期时间不为0，代表有过期时间
            else {
                jedis.setex(readKey,seconds,beanToString(value));
            }
            return true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;

    }


    /**
     * Json
     *
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }


    /**
     * 该key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }


    /**
     * key的value自增
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }


    /**
     * key的value自减
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }


    /**
     * JSON
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class clazz) {
        if (StringUtils.isBlank(str) || clazz == null) {
            return null;
        } else if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return (T) JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }


}
