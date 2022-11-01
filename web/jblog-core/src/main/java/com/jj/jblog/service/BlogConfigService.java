package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.entity.BlogConfig;

/**
 * 系统配置Service
 * @author 任人子
 * @date 2021/11/18  - {TIME}
 */
public interface BlogConfigService extends IService<BlogConfig> {

    /**
     * 获取用户发送的消息
     * @param blogComment
     */
    void getBlogCommentMessage(BlogComment blogComment);
}
