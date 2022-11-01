package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.entity.BlogCategory;

import java.util.List;

/**
 * 博客分类Service
 * @author 任人子
 * @date 2021/11/9  - {TIME}
 */
public interface BlogCategoryService extends IService<BlogCategory> {

    /**
     * 获取未删除的目录列表
     * @return
     */
    Result<List<BlogCategory>> getCategoryList();

    /**
     * 分页查询文章分类
     * @param condition
     * @param blogCategory
     * @return
     */
    Result<PageResult> pageCategory(PageCondition<BlogCategory> condition, BlogCategory blogCategory);

    /**
     * 更新分类状态
     * @param blogCategory
     * @return
     */
    Result<String> updateCategoryStatus(BlogCategory blogCategory);

    /**
     * 清除分类
     * @param categoryId
     * @return
     */
    Result<String> clearCategory(Integer categoryId);

    /**
     * 添加分类
     * @param blogCategory
     * @return
     */
    Result<String> addCategory(BlogCategory blogCategory);
}
