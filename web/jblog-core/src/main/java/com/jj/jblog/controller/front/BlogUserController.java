package com.jj.jblog.controller.front;

import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.pojo.dto.BlogLikesStatusDto;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.service.RedisService;
import com.jj.jblog.utils.IpAdrressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 博客用户Controller
 * @author 任人子
 * @date 2022/5/13  - {TIME}
 */
@Api(tags = "blogUserController", description = "前台用户模块")
@RequestMapping("/front/user")
@RestController()
public class BlogUserController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisService redisService;

    @ApiOperation("获取今天总日流量")
    @GetMapping("/getCurrentVisit")
    public Result<Integer> getCurrentVisit() {
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,
                redisService.hGetAll(StringConstants.BLOG_USER_VISIT_KEY).size());
    }

    @ApiOperation("用户访问状态查询")
    @GetMapping("/userVisit")
    public Result userVisit() {
        String hashKey = IpAdrressUtil.getIpAdrress(request);
        Integer count = (Integer) redisService.hGet(StringConstants.BLOG_USER_VISIT_KEY, hashKey);
        if (count != null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        redisService.hSet(StringConstants.BLOG_USER_VISIT_KEY, hashKey, 0, 24 * 60 * 60);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

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
        } else {
            blogLikesStatusDto.setBlogLikes(Long.valueOf(count));
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogLikesStatusDto);
    }

    @ApiOperation(value = "点赞博客")
    @PostMapping("/likeBlog")
    public Result<String> likeBlog(Long blogId) {
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
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, "点赞成功");
    }


}
