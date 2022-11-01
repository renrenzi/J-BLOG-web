package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.BlogTagRelationMapper;
import com.jj.jblog.entity.BlogTagRelation;
import com.jj.jblog.service.BlogTagRelationService;
import org.springframework.stereotype.Service;

/**
 * 博客标签关系ServiceImpl
 * @author 任人子
 * @date 2021/11/17  - {TIME}
 */
@Service
public class BlogTagRelationServiceImpl extends ServiceImpl<BlogTagRelationMapper, BlogTagRelation> implements BlogTagRelationService {
}
