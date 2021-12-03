package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.dao.BlogCommentMapper;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.service.BlogCommentService;
import com.jj.jblog.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @author 张俊杰
 * @date 2021/11/14  - {TIME}
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper,BlogComment> implements BlogCommentService {

    @Resource
    private BlogCommentMapper blogCommentMapper;

    @Override
    public Result<PageResult> pageComment(PageCondition<BlogComment> condition, BlogComment blogComment) {
        QueryWrapper<BlogComment> query = new QueryWrapper<>(blogComment);
        // 按创建时间逆序
        query.lambda().orderByDesc(BlogComment::getCommentCreateTime);
        // 页数和页号
        Page<BlogComment> page = new Page(condition.getPageNum(),condition.getPageSize());
        blogCommentMapper.selectPage(page,query);
        PageResult<BlogComment> result = new PageResult<BlogComment>();
        result.setTotalSize(page.getTotal());
        result.setData(page.getRecords());
        if (CollectionUtils.isEmpty(page.getRecords())){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,result);
    }

    @Override
    public Result<String> updateCommentStatus(BlogComment blogComment) {
        int result = blogCommentMapper.updateById(blogComment);
        if (result < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
