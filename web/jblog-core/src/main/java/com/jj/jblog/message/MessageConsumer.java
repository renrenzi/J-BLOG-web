package com.jj.jblog.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author 任人子
 * @date 2022/7/8  - {TIME}
 */
@Component
public class MessageConsumer {
    private static final Log LOG = LogFactory.getLog(MessageConsumer.class);

    @RabbitListener(queues = "CommentQueue")
    public void consumer(String message){
        LOG.info(" 收到消息  {}"+message);
    }
}
