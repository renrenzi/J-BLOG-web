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
import com.jj.jblog.dao.BlogCategoryMapper;
import com.jj.jblog.dao.BlogInfoMapper;
import com.jj.jblog.entity.BlogCategory;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.entity.BlogTag;
import com.jj.jblog.service.BlogCategoryService;
import com.jj.jblog.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 博客分类ServiceImpl
 * @author 任人子
 * @date 2021/11/9  - {TIME}
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper,BlogCategory> implements BlogCategoryService {

    @Resource
    private BlogCategoryMapper blogCategoryMapper;
    @Resource
    private BlogInfoMapper blogInfoMapper;
    /**
     * 分类的集合数据[用于下拉框]
     * @return
     */
    @Override
    public Result<List<BlogCategory>> getCategoryList() {
        QueryWrapper<BlogCategory> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                    .eq(BlogCategory::getIsDeleted, DeleteStatusEnum.NOT_DELETED.getStatus());
        List<BlogCategory> categoryList = blogCategoryMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,categoryList);
    }

    @Override
    public Result<PageResult> pageCategory(PageCondition<BlogCategory> condition, BlogCategory blogCategory) {
        QueryWrapper<BlogCategory> query = new QueryWrapper<BlogCategory>(blogCategory);
        query.lambda()
                .orderByAsc(BlogCategory::getCategoryRank);
        Page page = new Page(condition.getPageNum(),condition.getPageSize());
        blogCategoryMapper.selectPage(page,query);
        PageResult<BlogTag> result = new PageResult<BlogTag>();

        result.setTotalSize(page.getTotal());
        result.setData(page.getRecords());
        if (CollectionUtils.isEmpty(page.getRecords())){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,result);
    }

    @Override
    public Result<String> updateCategoryStatus(BlogCategory blogCategory) {
        int result = blogCategoryMapper.updateById(blogCategory);
        if (result < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> clearCategory(Integer categoryId) {
        BlogInfo blogInfo = new BlogInfo()
                            .setBlogCategoryId(Integer.valueOf(SysConfigConstants.DEFAULT_CATEGORY.getConfigField()))
                            .setBlogCategoryName(SysConfigConstants.DEFAULT_CATEGORY.getConfigName());
        QueryWrapper<BlogInfo> updateQuery = new QueryWrapper<BlogInfo>();
        updateQuery.lambda()
                    .eq(BlogInfo::getBlogCategoryId,categoryId);
        blogInfoMapper.update(blogInfo,updateQuery);
        if (blogCategoryMapper.deleteById(categoryId) < 1){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @Override
    public Result<String> addCategory(BlogCategory blogCategory) {
        blogCategory.setCreateTime(DateUtils.getLocalCurrentTime())
                    .setIsDeleted(DeleteStatusEnum.NOT_DELETED.getStatus());
        int result = blogCategoryMapper.insert(blogCategory);
        if (result < 1){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
