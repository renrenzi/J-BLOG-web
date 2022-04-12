package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.DeleteStatusEnum;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.dao.BlogCommentMapper;
import com.jj.jblog.dao.BlogInfoMapper;
import com.jj.jblog.dao.BlogTagRelationMapper;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.entity.BlogTagRelation;
import com.jj.jblog.pojo.dto.BlogInfoRequestDto;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 张俊杰
 * @date 2021/11/9  - {TIME}
 */
@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {
    @Resource
    private BlogInfoMapper blogInfoMapper;
    @Resource
    private BlogTagRelationMapper blogTagRelationMapper;
    @Resource
    private BlogCommentMapper blogCommentMapper;


    @Override
    public Result<String> saveBlog2(List<Integer> tagIds, BlogInfoRequestDto blogInfoRequestDto) {
        BlogInfo blogInfo = null;
        BeanUtils.copyProperties(blogInfoRequestDto, blogInfo);
        blogInfo.setCreateTime(DateUtils.getLocalCurrentTime());
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentTime());
        blogInfo.setBlogLikes(0L);
        if (blogInfoMapper.insert(blogInfo) > 0) {
            // 创建文章标签关系列表
            List<BlogTagRelation> list = new ArrayList<>();
            for (Integer tagId : tagIds) {
                list.add(new BlogTagRelation().setBlogId(blogInfo.getBlogId())
                        .setTagId(tagId));
            }

            // 删除已存在的关系
            blogTagRelationMapper.delete(new QueryWrapper<BlogTagRelation>()
                    .lambda()
                    .eq(BlogTagRelation::getBlogId, blogInfo.getBlogId()));
            for (BlogTagRelation item : list) {
                blogTagRelationMapper.insert(item);
            }
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Result<String> saveBlog(List<Integer> tagIds, BlogInfo blogInfo) {

        blogInfo.setCreateTime(DateUtils.getLocalCurrentTime());
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentTime());
        blogInfo.setBlogLikes(0L);
        if (blogInfoMapper.insert(blogInfo) > 0) {
            // 创建文章标签关系列表
            List<BlogTagRelation> list = new ArrayList<>();
            for (Integer tagId : tagIds) {
                list.add(new BlogTagRelation().setBlogId(blogInfo.getBlogId())
                        .setTagId(tagId));
            }
            // 删除已存在的关系
            blogTagRelationMapper.delete(new QueryWrapper<BlogTagRelation>()
                    .lambda()
                    .eq(BlogTagRelation::getBlogId, blogInfo.getBlogId()));
            for (BlogTagRelation item : list) {
                blogTagRelationMapper.insert(item);
            }
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }

        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Result<PageResult> pageBlog(PageCondition<BlogInfo> condition, BlogInfo blogInfo) {
        QueryWrapper<BlogInfo> query = new QueryWrapper<BlogInfo>(blogInfo);
        query.lambda()
                .orderByDesc(BlogInfo::getUpdateTime);
        Page<BlogInfo> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        blogInfoMapper.selectPage(page, query);
        PageResult<BlogInfo> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        pageResult.setData(page.getRecords());
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, pageResult);
    }

    @Override
    public Result<String> updateBlogStatus(BlogInfo blogInfo) {
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentTime());
        int result = blogInfoMapper.updateById(blogInfo);
        if (result < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @Override
    public Result<String> deleteBlog(Long blogId) {
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setIsDeleted(DeleteStatusEnum.DELETED.getStatus())
                .setUpdateTime(DateUtils.getLocalCurrentTime());
        int result = blogInfoMapper.updateById(blogInfo);
        if (result < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Result<String> clearBlog(Long blogId) {
        // 成功删除该文章
        if (SqlHelper.retBool(blogInfoMapper.deleteById(blogId))) {
            QueryWrapper<BlogTagRelation> tagRelationQueryWrapper = new QueryWrapper<>();
            tagRelationQueryWrapper.lambda()
                    .eq(BlogTagRelation::getBlogId, blogId);
            // 删除文章评论关系
            blogTagRelationMapper.delete(tagRelationQueryWrapper);
            QueryWrapper<BlogComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.lambda()
                    .eq(BlogComment::getBlogId, blogId);
            // 删除文章全部评论
            blogCommentMapper.delete(commentQueryWrapper);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
    }

    @Override
    public Result<String> restoreBlog(Long blogId) {
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setIsDeleted(DeleteStatusEnum.NOT_DELETED.getStatus())
                .setUpdateTime(DateUtils.getLocalCurrentTime());
        int result = blogInfoMapper.updateById(blogInfo);
        if (result < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @Override
    public Result<List<BlogInfo>> getAllBlog() {

        QueryWrapper<BlogInfo> query = new QueryWrapper<>();
        query.lambda().eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NOT_DELETED.getStatus());
        List<BlogInfo> blogInfoList = blogInfoMapper.selectList(query);
        if (CollectionUtils.isEmpty(blogInfoList)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogInfoList);
    }
}
