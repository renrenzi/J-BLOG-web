package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.DeleteStatusEnum;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.SysConfigConstants;
import com.jj.jblog.dao.BlogTagMapper;
import com.jj.jblog.dao.BlogTagRelationMapper;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.entity.BlogTag;
import com.jj.jblog.entity.BlogTagRelation;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.service.BlogTagRelationService;
import com.jj.jblog.service.BlogTagService;
import com.jj.jblog.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客标签ServiceImpl
 * @author 任人子
 * @date 2021/11/10  - {TIME}
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper,BlogTag> implements BlogTagService {
    @Resource
    private BlogTagMapper blogTagMapper;
    @Resource
    private BlogTagRelationMapper tagRelationMapper;
    @Resource
    private BlogTagRelationService tagRelationService;
    @Resource
    private BlogInfoService blogInfoService;

    @Override
    public Result<List<BlogTag>> getBlogTags() {
        QueryWrapper<BlogTag> query = new QueryWrapper<>();
        query.lambda()
                .eq(BlogTag::getIsDeleted, DeleteStatusEnum.NOT_DELETED.getStatus());
        List<BlogTag> blogTagList = blogTagMapper.selectList(query);
        if (CollectionUtils.isEmpty(blogTagList)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogTagList);
    }

    @Override
    public Result<PageResult> pageBlogTag(PageCondition<BlogTag> condition, BlogTag blogTag) {
        QueryWrapper<BlogTag> query = new QueryWrapper<BlogTag>(blogTag);
        query.lambda()
                .orderByDesc(BlogTag::getTagId);
        Page page = new Page(condition.getPageNum(), condition.getPageSize());
        blogTagMapper.selectPage(page, query);
        PageResult<BlogTag> result = new PageResult<BlogTag>();

        result.setTotalSize(page.getTotal());
        result.setData(page.getRecords());
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    @Override
    public Result<String> updateTagStatus(BlogTag blogTag) {
        BlogTag sqlBlogTag = blogTagMapper.selectById(blogTag.getTagId());
        if (sqlBlogTag != null) {
            int result = blogTagMapper.updateById(blogTag);
            if (result < 0) {
                return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
            }
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Result<String> clearTag(Integer tagId) {
        QueryWrapper<BlogTagRelation> query = new QueryWrapper();
        List<BlogTagRelation> relationList = tagRelationService.list(query);
        List<BlogInfo> blogInfoList = relationList.stream()
                .map(tagRelation -> new BlogInfo()
                        .setBlogId(tagRelation.getBlogId())
                        .setBlogTags(SysConfigConstants.DEFAULT_TAG.getConfigName()))
                .collect(Collectors.toList());

        List<Long> listIds = blogInfoList.stream()
                .map(BlogInfo::getBlogId)
                .collect(Collectors.toList());

        List<BlogTagRelation> newTagRelationList = relationList.stream()
                .map(tagRelation -> new BlogTagRelation()
                        .setTagId(Integer.valueOf(SysConfigConstants.DEFAULT_TAG.getConfigField()))
                        .setBlogId(tagRelation.getBlogId()))
                .collect(Collectors.toList());

        blogInfoService.updateBatchById(blogInfoList);

        tagRelationService.remove(new QueryWrapper<BlogTagRelation>()
                .lambda()
                .in(BlogTagRelation::getBlogId, listIds));

        tagRelationService.saveBatch(newTagRelationList);
        if (blogTagMapper.deleteById(tagId) < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @Override
    public Result<String> addTag(BlogTag blogTag) {
        blogTag.setCreateTime(DateUtils.getLocalCurrentTime())
                .setIsDeleted(DeleteStatusEnum.NOT_DELETED.getStatus());
        int result = blogTagMapper.insert(blogTag);
        if (result < 1){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
