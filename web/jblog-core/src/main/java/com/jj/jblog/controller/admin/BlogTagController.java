package com.jj.jblog.controller.admin;

import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.BlogTag;
import com.jj.jblog.service.BlogTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 博客标签管理
 * @author 任人子
 * @date 2021/11/10  - {TIME}
 */
@Api(tags = "blogLinkController", description = "博客标签管理")
@RequestMapping("/blog/tag")
@RestController
public class BlogTagController {

    @Resource
    private BlogTagService blogTagService;

    @ApiOperation(value = "过去未删除的标签列表")
    @GetMapping("/getBlogTag")
    public Result<List<BlogTag>> getBlogTagList(){
        return blogTagService.getBlogTags();
    }

    @ApiOperation(value = "分页查询标签列表")
    @PostMapping("/pageBlogTag")
    public Result<PageResult> pageBlogTag(PageCondition<BlogTag> condition, BlogTag blogTag) {
        // 传参问题,未解决
        if ("".equals(blogTag.getTagName())){
            blogTag.setTagName(null);
        }
        if(StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.pageBlogTag(condition,blogTag);
    }

    @ApiOperation(value = "更新标签状态")
    @PostMapping("/tagStatus")
    public Result<String> updateTagStatus(BlogTag blogTag){
        if (ObjectUtils.isEmpty(blogTag)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.updateTagStatus(blogTag);
    }

    @ApiOperation(value = "清除标签数据库")
    @PostMapping("/tagClear")
    public Result<String> clearTag(Integer tagId){
        if (StringUtils.isEmpty(tagId)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.clearTag(tagId);
    }

    @ApiOperation(value = "添加标签")
    @PostMapping("/addTag")
    public Result<String> addTag(BlogTag blogTag){
        if(ObjectUtils.isEmpty(blogTag)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.addTag(blogTag);
    }

    @ApiOperation(value = "获取标签")
    @PostMapping("/getTag")
    public Result<BlogTag> getTag(Integer tagId){
        if (StringUtils.isEmpty(tagId)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogTag blogTag = blogTagService.getById(tagId);
        if (ObjectUtils.isEmpty(blogTag)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,blogTag);
    }
}
