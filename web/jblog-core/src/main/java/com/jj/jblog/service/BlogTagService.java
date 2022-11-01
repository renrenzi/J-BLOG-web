package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.entity.BlogTag;

import java.util.List;

/**
 * 博客标签Service
 * @author 任人子
 * @date 2021/11/10  - {TIME}
 */
public interface BlogTagService extends IService<BlogTag> {

    /**
     * 过去未删除的标签列表
     * @return
     */
    Result<List<BlogTag>> getBlogTags();

    /**
     * 分页查询标签列表
     * @param condition
     * @param blogTag
     * @return
     */
    Result<PageResult> pageBlogTag(PageCondition<BlogTag> condition, BlogTag blogTag);

    /**
     * 更新标签状态
     * @param blogTag
     * @return
     */
    Result<String> updateTagStatus(BlogTag blogTag);

    /**
     * 清除标签数据库
     * @param tagId
     * @return
     */
    Result<String> clearTag(Integer tagId);

    /**
     * 添加标签
     * @param blogTag
     * @return
     */
    Result<String> addTag(BlogTag blogTag);
}
