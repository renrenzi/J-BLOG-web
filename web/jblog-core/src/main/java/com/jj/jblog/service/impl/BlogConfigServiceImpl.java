package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.dao.BlogConfigMapper;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.entity.BlogConfig;
import com.jj.jblog.service.BlogConfigService;
import com.jj.jblog.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统配置ServiceImpl
 * @author 任人子
 * @date 2021/11/18  - {TIME}
 */
@Slf4j
@Service
public class BlogConfigServiceImpl extends ServiceImpl<BlogConfigMapper, BlogConfig> implements BlogConfigService {

    @Resource
    private RedisService redisService;

    @Override
    @RabbitListener(queues = "CommentQueue")
    public void getBlogCommentMessage(BlogComment blogComment) {

        redisService.lPush(StringConstants.BLOG_COMMEENT_QUEUE, blogComment);
    }


}
