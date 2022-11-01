package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.entity.BlogLink;

/**
 * 友链Service
 * @author 任人子
 * @date 2021/11/19  - {TIME}
 */
public interface BlogLinkService extends IService<BlogLink> {

    /**
     * 分页查询链接列表
     * @param condition
     * @param blogLink
     * @return
     */
    Result<PageResult<BlogLink>> pageLinkList(PageCondition<BlogLink> condition, BlogLink blogLink);
}
