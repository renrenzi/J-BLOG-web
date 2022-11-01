package com.jj.jblog.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq queue 配置
 * @author 任人子
 * @date 2022/5/8  - {TIME}
 */
@Configuration
public class AmqpConfig {

    @Value("${spring.rabbitmq.ExchangeName}")
    private String exchange;
    @Value("${spring.rabbitmq.RoutingKey}")
    private String routingKey;
    @Value("${spring.rabbitmq.Queue}")
    private String commentQueue;

    /**
     * Direct Exchange
     * @return
     */
    @Bean
    public DirectExchange myChange(){
        return new DirectExchange(exchange);
    }

    /**
     * comment Queue
     * @return
     */
    @Bean
    public Queue commentQueue(){
        return new Queue(commentQueue);
    }

    /**
     * 将队列绑定到交换机
     * @param queue
     * @param myChange
     * @return
     */
    @Bean("binding")
    public Binding binding(Queue queue, Exchange myChange){
        return BindingBuilder.bind(queue).to(myChange).with(routingKey).noargs();
    }

}
