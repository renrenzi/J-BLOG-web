package com.jj.jblog.controller.admin;

import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.dao.BlogCommentMapper;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.service.BlogCommentService;
import com.jj.jblog.service.RedisService;
import com.jj.jblog.util.SensitiveUtil;
import com.jj.jblog.utils.DateUtils;
import com.jj.jblog.utils.IpAdrressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 博客评论管理
 * @author 任人子
 * @date 2021/11/14  - {TIME}
 */
@Api(tags = "blogCommentController", description = "博客评论管理")
@RequestMapping("/blog/comment")
@RestController
public class BlogCommentController {

    @Resource
    private BlogCommentService blogCommentService;
    @Resource
    private BlogCommentMapper blogCommentMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private SensitiveUtil sensitiveUtil;
    @Resource
    private AmqpTemplate amqpTemplate;
    @Value("${spring.rabbitmq.ExchangeName}")
    private String exchange;
    @Value("${spring.rabbitmq.RoutingKey}")
    private String routingKey;

    @ApiOperation(value = "发布评论")
    @PostMapping("/createComment")
    public Result<String> createComment(BlogComment blogComment) {
        String requestIp = IpAdrressUtil.getIpAdrress(request);
        String key = StringConstants.COMMENT_WEBSITE_PREFIX + requestIp;
        if (redisService.get(key) != null) {
            return ResultGenerator.getResultByMsg(HttpStatusEnum.BAD_GATEWAY, "30秒内评论过, 请稍后评论");
        }
        redisService.set(key, requestIp);
        blogComment.setCommentBody(sensitiveUtil.filter(blogComment.getCommentBody()));
        // 设置超时30s
        redisService.expire(key, 30);
        blogComment.setCommentatorIp(requestIp)
                .setCommentator("IP : " + requestIp + "的游客")
                .setCommentCreateTime(DateUtils.getLocalCurrentTime())
                .setReplyCreateTime(null)
                .setIsDeleted(0)
                .setCommentStatus(0);

        // 向消息队列发送评论消息
        amqpTemplate.convertAndSend(exchange, routingKey, blogComment);
        if (blogCommentMapper.insert(blogComment) > 0) {
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

    @ApiOperation(value = "更新评论部分状态")
    @PostMapping("/commentStatus")
    public Result<String> updateCommentStatus(BlogComment blogComment) {
        if (ObjectUtils.isEmpty(blogComment)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCommentService.updateCommentStatus(blogComment);
    }

    @ApiOperation(value = "清除评论")
    @PostMapping("/deleteComment")
    public Result<String> deleteComment(@RequestParam Long commentId) {
        if (StringUtils.isEmpty(commentId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (blogCommentMapper.deleteById(commentId) < 0) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation(value = "获取评论信息")
    @PostMapping("/getComment")
    public Result<BlogComment> getComment(@RequestParam Long commentId) {
        if (StringUtils.isEmpty(commentId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogComment blogComment = blogCommentMapper.selectById(commentId);
        if (ObjectUtils.isEmpty(blogComment)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogComment);
    }

}
