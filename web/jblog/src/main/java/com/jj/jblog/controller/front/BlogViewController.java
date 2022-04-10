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
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.dao.BlogCommentMapper;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.entity.BlogTagRelation;
import com.jj.jblog.pojo.dto.BlogLikesStatusDto;
import com.jj.jblog.pojo.dto.BlogViewRequest;
import com.jj.jblog.service.BlogCommentService;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.service.BlogTagRelationService;
import com.jj.jblog.service.RedisService;
import com.jj.jblog.util.DateUtils;
import com.jj.jblog.util.IpAdrressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 博客前台展示接口
 *
 * @author 任人子
 * @date 2021/11/26  - {TIME}
 */
@Api(tags = "blogViewController", description = "博客前台展示接口")
@RestController
@RequestMapping("/front")
public class BlogViewController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogTagRelationService blogTagRelationService;
    @Resource
    private BlogCommentService blogCommentService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisService redisService;


    @ApiOperation(value = "获取当前访客的点赞状态")
    @PostMapping("/getCurrentLikeStatus")
    public Result<BlogLikesStatusDto> getCurrentLikeStatus(Long blogId) {
        if (blogId == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogLikesStatusDto blogLikesStatusDto = new BlogLikesStatusDto();
        blogLikesStatusDto.setBlogId(blogId);
        String likeKey = IpAdrressUtil.getIpAdrress(request) + ":" + blogId;
        Integer isLike = (Integer) redisService.hGet(StringConstants.BLOG_LIKES_PREFIX, likeKey);

        if (isLike != null) {
            // 该访客点赞过
            blogLikesStatusDto.setLikeStatus(isLike);
        } else {
            // 该访客未点赞过
            blogLikesStatusDto.setLikeStatus(0);
        }
        String countKey = blogId + "";
        Integer count = (Integer) redisService.hGet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey);
        if (count == null) {
            // Redis不存在, 重新设置在Redis中, 并将点赞数据返回
            Long blogLikes = blogInfoService.getById(blogId).getBlogLikes();
            blogLikesStatusDto.setBlogLikes(blogLikes);
            redisService.hSet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey, blogLikes);
        }else{
            blogLikesStatusDto.setBlogLikes(Long.valueOf(count));
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogLikesStatusDto);
    }

    @ApiOperation(value = "点赞博客")
    @PostMapping("/likeBlog")
    public Result likeBlog(Long blogId) {
        if (blogId == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        String likeKey = IpAdrressUtil.getIpAdrress(request) + ":" + blogId;
        String countKey = blogId + "";
        Integer isLike = (Integer) redisService.hGet(StringConstants.BLOG_LIKES_PREFIX, likeKey);
        Integer count = (Integer) redisService.hGet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey);
        // 是否点赞过
        if (isLike == null) {
            redisService.hSet(StringConstants.BLOG_LIKES_PREFIX, likeKey, 1);
            if (count == null) {
                redisService.hSet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey, blogInfoService.getById(blogId).getBlogLikes() + 1);
            } else {
                redisService.hSet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey, count + 1);
            }
        } else {
            int increment = isLike == 0 ? 1 : -1;
            redisService.hSet(StringConstants.BLOG_LIKES_PREFIX, likeKey, isLike == 0 ? 1 : 0);
            if (count == null) {
                redisService.hSet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey, (blogInfoService.getById(blogId).getBlogLikes() + increment));
            } else {
                redisService.hSet(StringConstants.BLOG_LIKES_COUNT_PREFIX, countKey, count + increment);
            }
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation(value = "分页条件查询博客列表")
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/pageBlogView")
    public Result<PageResult<BlogInfo>> pageBlogView(BlogViewRequest conditions) {
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
        PageResult<BlogInfo> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        if (Objects.isNull(page.getRecords())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        List<BlogInfo> data = page.getRecords();

        Map<Object, Object> map = redisService.hGetAll(StringConstants.BLOG_LIKES_COUNT_PREFIX);
        if(!map.isEmpty()){
            for(BlogInfo info: data) {
                if(map.containsKey("" + info.getBlogId())){
                    info.setBlogLikes(Long.valueOf((Integer)map.get("" + info.getBlogId())));
                }
            }
        }
        pageResult.setData(data);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, pageResult);
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
}
