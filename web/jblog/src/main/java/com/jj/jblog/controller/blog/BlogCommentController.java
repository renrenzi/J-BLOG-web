package com.jj.jblog.controller.blog;

import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.dao.BlogCommentMapper;
import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.service.BlogCommentService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 张俊杰
 * @date 2021/11/14  - {TIME}
 */
@RequestMapping("comment")
@RestController
public class BlogCommentController {

    @Resource
    private BlogCommentService blogCommentService;
    @Resource
    private BlogCommentMapper blogCommentMapper;

    /**
     * 分页查询文章评论
     * @param condition
     * @param blogComment
     * @return
     */
    @PostMapping("/pageComment")
    public Result<PageResult> pageComment(PageCondition<BlogComment> condition, BlogComment blogComment){
        if (StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCommentService.pageComment(condition,blogComment);
    }

    /**
     * 更新评论部分状态
     * @param blogComment
     * @return
     */
    @PostMapping("/commentStatus")
    public Result<String> updateCommentStatus(BlogComment blogComment){
        if (ObjectUtils.isEmpty(blogComment)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCommentService.updateCommentStatus(blogComment);
    }

    /**
     * 清除评论
     * @param commentId
     * @return
     */
    @PostMapping("/deleteComment")
    public Result<String> deleteComment(@RequestParam Long commentId){
        if (StringUtils.isEmpty(commentId)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (blogCommentMapper.deleteById(commentId) < 0){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * 获取评论信息
     * @param commentId
     * @return
     */
    @PostMapping("/getComment")
    public Result<BlogComment> getComment(@RequestParam Long commentId){
        if (StringUtils.isEmpty(commentId)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogComment blogComment = blogCommentMapper.selectById(commentId);
        if (ObjectUtils.isEmpty(blogComment)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,blogComment);
    }

}
