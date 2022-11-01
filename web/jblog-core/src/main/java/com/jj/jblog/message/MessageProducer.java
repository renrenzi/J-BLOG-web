package com.jj.jblog.message;

import cn.hutool.json.JSONUtil;
import com.jj.jblog.pojo.dto.MessageDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息生产者
 * @author 任人子
 * @date 2022/7/7  - {TIME}
 */
@Component
public class MessageProducer {

    @Resource
    private AmqpTemplate amqpTemplate;
    @Value("${spring.rabbitmq.ExchangeName}")
    private String exchange;
    @Value("${spring.rabbitmq.RoutingKey}")
    private String routingKey;

    public void sendMessage(MessageDto message){
        amqpTemplate.convertAndSend(exchange, routingKey, JSONUtil.toJsonStr(message));
    }
}
