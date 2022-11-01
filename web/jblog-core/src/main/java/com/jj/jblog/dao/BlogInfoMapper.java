package com.jj.jblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jj.jblog.entity.BlogInfo;

import java.util.List;

/**
 * 博客详情Mapper
 * @author 任人子
 * @date 2021/11/5  - {TIME}
 */
public interface BlogInfoMapper extends BaseMapper<BlogInfo> {

    List<BlogInfo> getMaxCommentBlog();
}
