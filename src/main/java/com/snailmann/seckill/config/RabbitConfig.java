package com.snailmann.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {

    public static final String SECKILL_QUEUE = "seckill.order";

    /**
     * 声明mq队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(SECKILL_QUEUE,true);
    }

}
