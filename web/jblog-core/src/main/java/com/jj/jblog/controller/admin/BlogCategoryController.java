package com.jj.jblog.controller.admin;

import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.BlogCategory;
import com.jj.jblog.service.BlogCategoryService;
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
 * 博客分类管理
 * @author 任人子
 * @date 2021/11/10  - {TIME}
 */
@Api(tags = "blogCategoryController", description = "博客分类管理")
@RequestMapping("/blog/category")
@RestController
public class BlogCategoryController {

    @Resource
    private BlogCategoryService blogCategoryService;

    @ApiOperation(value = "获取所有分类列表")
    @GetMapping("/list")
    public Result<List<BlogCategory>> categoryList() {
        return blogCategoryService.getCategoryList();
    }

    @ApiOperation(value = "分页查询分类列表")
    @PostMapping("/pageCategory")
    public Result<PageResult> pageCategory(PageCondition<BlogCategory> condition, BlogCategory blogCategory) {
        // 传参问题,未解决
        if ("".equals(blogCategory.getCategoryName())) {
            blogCategory.setCategoryName(null);
        }
        if (StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCategoryService.pageCategory(condition, blogCategory);
    }

    @ApiOperation(value = "更新分类状态")
    @PostMapping("/categoryStatus")
    public Result<String> updateCategoryStatus(BlogCategory blogCategory) {
        if (ObjectUtils.isEmpty(blogCategory)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCategoryService.updateCategoryStatus(blogCategory);
    }

    @ApiOperation(value = "清除分类")
    @PostMapping("/clearCategory")
    public Result<String> clearCategory(Integer categoryId) {
        if (StringUtils.isEmpty(categoryId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCategoryService.clearCategory(categoryId);
    }

    @ApiOperation(value = "获取分类")
    @PostMapping("/getCategory")
    public Result<BlogCategory> getCategory(Integer categoryId) {
        if (StringUtils.isEmpty(categoryId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogCategory blogCategory = blogCategoryService.getById(categoryId);
        if (ObjectUtils.isEmpty(blogCategory)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogCategory);
    }

    @ApiOperation(value = "添加分类")
    @PostMapping("/addCategory")
    public Result<String> addCategory(BlogCategory blogCategory) {
        if (ObjectUtils.isEmpty(blogCategory)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogCategoryService.addCategory(blogCategory);
    }
}
