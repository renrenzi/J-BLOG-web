package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.entity.BlogComment;

/**
 * @author 张俊杰
 * @date 2021/11/14  - {TIME}
 */
public interface BlogCommentService extends IService<BlogComment> {

    /**
     * 分页查询文章评论
     * @param condition
     * @param blogComment
     * @return
     */
    Result<PageResult> pageComment(PageCondition<BlogComment> condition, BlogComment blogComment);

    /**
     * 更新博客部分状态
     * @param blogComment
     * @return
     */
    Result<String> updateCommentStatus(BlogComment blogComment);
}
