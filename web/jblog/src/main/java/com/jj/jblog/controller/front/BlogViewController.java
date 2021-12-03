package com.jj.jblog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.BlogStatusEnum;
import com.jj.jblog.constant.DeleteStatusEnum;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.entity.BlogTagRelation;
import com.jj.jblog.pojo.dto.BlogViewRequest;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.service.BlogTagRelationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 博客前台展示接口
 *
 * @author 任人子
 * @date 2021/11/26  - {TIME}
 */
@RestController
@RequestMapping("front")
public class BlogViewController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogTagRelationService blogTagRelationService;

    /**
     * 分页条件查询博客列表
     *
     * @param conditions
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/pageBlogView")
    public Result<PageResult<BlogInfo>> pageBlogView(BlogViewRequest conditions) {
        if (conditions == null || conditions.getPageNum() == null || conditions.getPageSize() == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if ("".equals(conditions.getCategoryName())){
            conditions.setCategoryName(null);
        }
        LambdaQueryWrapper<BlogInfo> sqlQuery = Wrappers.<BlogInfo>lambdaQuery()
                .like(Objects.nonNull(conditions.getKeyWord()), BlogInfo::getBlogTitle, conditions.getKeyWord())
                .eq(Objects.nonNull(conditions.getCategoryName()), BlogInfo::getBlogCategoryName, conditions.getCategoryName())
                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NOT_DELETED.getStatus())
                .orderByDesc(BlogInfo::getCreateTime);
        // 标签条件
        if (Objects.nonNull(conditions.getTagId())) {
            List<BlogTagRelation> list = blogTagRelationService.list(new QueryWrapper<BlogTagRelation>().lambda()
                    .eq(BlogTagRelation::getTagId, conditions.getTagId()));
            sqlQuery.in(BlogInfo::getBlogId,list.stream().map(BlogTagRelation::getBlogId).toArray());
        }
        Page<BlogInfo> page = new Page<>(conditions.getPageNum(), conditions.getPageSize());
        blogInfoService.page(page, sqlQuery);
        PageResult<BlogInfo> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        if (Objects.isNull(page.getRecords())){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        pageResult.setData(page.getRecords());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,pageResult);
    }
    @PostMapping("/blogDetail")
    public Result<BlogInfo> blogDetail(Long blogId){
        if (StringUtils.isEmpty(blogId)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogInfo blogInfo = blogInfoService.getById(blogId);
        if(blogInfo == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        blogInfoService.updateById(new BlogInfo().setBlogId(blogId)
                                                 .setBlogViews(blogInfo.getBlogViews() + 1));
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,blogInfo);
    }
}
