package com.jj.jblog.controller.blog;

import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.BlogTag;
import com.jj.jblog.service.BlogTagService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张俊杰
 * @date 2021/11/10  - {TIME}
 */
@RequestMapping("tag")
@RestController
public class BlogTagController {

    @Resource
    private BlogTagService blogTagService;

    /**
     * 过去未删除的标签列表
     * @return
     */
    @GetMapping("/getBlogTag")
    public Result<List<BlogTag>> getBlogTagList(){
        return blogTagService.getBlogTags();
    }

    /**
     * 分页查询标签列表
     * @param condition
     * @param blogTag
     * @return
     */
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

    /**
     * 更新标签状态
     * @param blogTag
     * @return
     */
    @PostMapping("/tagStatus")
    public Result<String> updateTagStatus(BlogTag blogTag){
        if (ObjectUtils.isEmpty(blogTag)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.updateTagStatus(blogTag);
    }

    /**
     * 清除标签数据库
     * @param tagId
     * @return
     */
    @PostMapping("/tagClear")
    public Result<String> clearTag(Integer tagId){
        if (StringUtils.isEmpty(tagId)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.clearTag(tagId);
    }

    /**
     * 添加标签
     * @param blogTag
     * @return
     */
    @PostMapping("/addTag")
    public Result<String> addTag(BlogTag blogTag){
        if(ObjectUtils.isEmpty(blogTag)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogTagService.addTag(blogTag);
    }

    /**
     * 获取标签
     * @param tagId
     * @return
     */
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
