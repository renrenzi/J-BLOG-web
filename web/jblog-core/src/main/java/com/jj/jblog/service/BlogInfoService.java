package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.pojo.dto.BlogInfoRequestDto;

import java.util.List;

/**
 * 博客详情Service
 * @author 任人子
 * @date 2021/11/5  - {TIME}
 */
public interface BlogInfoService extends IService<BlogInfo> {
    List<BlogInfo> getMaxCommentBlog();
    /**
     * 保存文章内容
     * @param tagIds
     * @param blogInfo
     * @return com.jj.jblog.basic.Result
     */
    Result<String> saveBlog2(List<Integer> tagIds,  BlogInfoRequestDto blogInfoRequestDto);
    /**
     * 保存文章内容
     * @param tagIds
     * @param blogInfo
     * @return com.jj.jblog.basic.Result
     */
    Result<String> saveBlog(List<Integer> tagIds,  BlogInfo blogInfo);

    /**
     *  分页文章列表
     * @param condition
     * @param blogInfo
     * @return
     */
    Result<PageResult> pageBlog(PageCondition<BlogInfo> condition, BlogInfo blogInfo);

    /**
     *  更新文章状态
     * @param blogInfo
     * @return
     */
    Result<String> updateBlogStatus(BlogInfo blogInfo);

    /**
     * 更新文章删除状态
     * @param blogId
     * @return
     */
    Result<String> deleteBlog(Long blogId);

    /**
     * 清除文章数据库数据
     * @param blogId
     * @return
     */
    Result<String> clearBlog(Long blogId);

    /**
     * 还原文章
     * @param blogId
     * @return
     */
    Result<String> restoreBlog(Long blogId);

    /**
     * 获取所有博客
     * @return
     */
    Result<List<BlogInfo>> getAllBlog();
}
