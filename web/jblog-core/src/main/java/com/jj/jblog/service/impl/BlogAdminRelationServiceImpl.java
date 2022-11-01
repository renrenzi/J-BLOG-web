package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.BlogAdminRelationMapper;
import com.jj.jblog.entity.BlogAdminRelation;
import com.jj.jblog.service.BlogAdminRelationService;
import org.springframework.stereotype.Service;

/**
 * 博客用户关系ServiceImpl
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
@Service
public class BlogAdminRelationServiceImpl extends ServiceImpl<BlogAdminRelationMapper, BlogAdminRelation> implements BlogAdminRelationService {
}
