package com.snailmann.seckill.config;



import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;



@Configuration
public class RedisConfig {



    @Bean
    public JedisPool getJedisPool(){  //Redis连接池连接
        GenericObjectPoolConfig poolConfig = new JedisPoolConfig();
        return new JedisPool(poolConfig,"127.0.0.1",6379);
    }

    private String host;
    private int port;

}
