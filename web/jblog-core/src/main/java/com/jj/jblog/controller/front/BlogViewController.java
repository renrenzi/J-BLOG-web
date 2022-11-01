package com.jj.jblog.controller.front;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.entity.BlogTag;
import com.jj.jblog.pojo.dto.BlogTopBlogResponseDto;
import com.jj.jblog.service.RedisService;
import com.jj.jblog.constant.BlogStatusEnum;
import com.jj.jblog.constant.DeleteStatusEnum;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.entity.BlogTagRelation;
import com.jj.jblog.pojo.dto.BlogInfoResponseDto;
import com.jj.jblog.pojo.dto.BlogLikesStatusDto;
import com.jj.jblog.pojo.dto.BlogViewRequest;
import com.jj.jblog.service.*;
import com.jj.jblog.utils.DateUtils;
import com.jj.jblog.utils.IpAdrressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 博客前台展示接口
 *
 * @author 任人子
 * @date 2021/11/26  - {TIME}
 */
@Api(tags = "blogViewController", description = "博客前台展示接口")
@RestController
@RequestMapping("/front/view")
public class BlogViewController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogTagRelationService blogTagRelationService;
    @Resource
    private BlogCommentService blogCommentService;
    @Resource
    private RedisService redisService;
    @Resource
    private BlogCategoryService blogCategoryService;
    @Resource
    private BlogTagService blogTagService;

    @ApiOperation("获取所有标签")
    @GetMapping("/getAllTags")
    public Result<List<BlogTag>> getAllTags(){
        return blogTagService.getBlogTags();
    }

    @ApiOperation("获取热门评论和浏览量文章")
    @GetMapping("/getTopBlogAndComment")
    public Result<BlogTopBlogResponseDto> getTopBlogAndComment() {
        QueryWrapper<BlogInfo> query = new QueryWrapper<>();
        query.lambda().eq(BlogInfo::getIsDeleted, 0)
                .eq(BlogInfo::getEnableComment, 1)
                .eq(BlogInfo::getBlogStatus,BlogStatusEnum.RELEASE.getStatus())
                .orderByDesc(BlogInfo::getBlogViews);
        // 获取Top5文章()
        Page<BlogInfo> page = new Page<>(1, 5);
        blogInfoService.page(page, query);
        BlogTopBlogResponseDto data = new BlogTopBlogResponseDto();
        data.setBlogInfoList(page.getRecords());
        data.setBlogCommentList(blogInfoService.getMaxCommentBlog());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, data);
    }


    @ApiOperation(value = "分页条件查询博客列表")
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/pageBlogView")
    public Result<PageResult<BlogInfoResponseDto>> pageBlogView(BlogViewRequest conditions) {
        if (conditions == null || conditions.getPageNum() == null || conditions.getPageSize() == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if ("".equals(conditions.getCategoryName())) {
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
            sqlQuery.in(BlogInfo::getBlogId, list.stream().map(BlogTagRelation::getBlogId).toArray());
        }
        Page<BlogInfo> page = new Page<>(conditions.getPageNum(), conditions.getPageSize());
        blogInfoService.page(page, sqlQuery);
        PageResult<BlogInfoResponseDto> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        if (Objects.isNull(page.getRecords())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        List<BlogInfo> data = page.getRecords();
        setPageDateBlogLikes(data);
        pageResult.setData(setBlogCategoryIcon(data));
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, pageResult);
    }

    /**
     * 对分页查询的数据添加分类Icon
     *
     * @param data
     * @return
     */
    private List<BlogInfoResponseDto> setBlogCategoryIcon(List<BlogInfo> data) {
        List<BlogInfoResponseDto> blogInfoResponseList = new ArrayList<>();
        for (BlogInfo blogInfo : data) {
            BlogInfoResponseDto blogInfoResponseDto = new BlogInfoResponseDto();
            BeanUtils.copyProperties(blogInfo, blogInfoResponseDto);
            blogInfoResponseList.add(blogInfoResponseDto);
        }
        blogInfoResponseList.forEach(blogInfoResponseDto -> {
            blogInfoResponseDto.setCategoryIcon(blogCategoryService.getById(blogInfoResponseDto.getBlogCategoryId()).getCategoryIcon());
        });
        return blogInfoResponseList;
    }

    /**
     * 对分页查询的数据添加点赞数据
     *
     * @param data
     */
    private void setPageDateBlogLikes(List<BlogInfo> data) {
        Map<Object, Object> map = redisService.hGetAll(StringConstants.BLOG_LIKES_COUNT_PREFIX);
        if (!map.isEmpty()) {
            for (BlogInfo info : data) {
                if (map.containsKey("" + info.getBlogId())) {
                    info.setBlogLikes(Long.valueOf((Integer) map.get("" + info.getBlogId())));
                }
            }
        }
    }

    @ApiOperation(value = "获取博客详情")
    @PostMapping("/blogDetail")
    public Result<BlogInfo> blogDetail(Long blogId) {
        if (StringUtils.isEmpty(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        // 浏览量缓存前缀Key
        String key = StringConstants.BLOG_VIEWS_PREFIX + blogId;
        BlogInfo blogInfo = blogInfoService.getById(blogId);
        if (blogInfo == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        blogInfoService.updateById(new BlogInfo().setBlogId(blogId)
                .setBlogViews(blogInfo.getBlogViews() + 1));
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogInfo);
    }

    @ApiOperation(value = "回复评论")
    @PostMapping("/replyComment")
    public Result<String> replyComment(BlogComment blogComment) {
        if (blogComment == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(blogComment.getReplyBody()) || blogComment.getCommentId() == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        blogComment.setReplyCreateTime(DateUtils.getLocalCurrentTime());
        boolean count = blogCommentService.updateById(blogComment);
        if (count) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "分页查询文章评论")
    @PostMapping("/pageComment")
    public Result<PageResult> pageComment(PageCondition<BlogComment> condition, BlogComment blogComment) {
        if (StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCommentService.pageComment(condition, blogComment);
    }

}
