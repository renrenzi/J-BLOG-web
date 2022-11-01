package com.jj.jblog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.UserResourceCategory;
import com.jj.jblog.service.UserResourceCategoryService;
import com.jj.jblog.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户资源分类管理Controller
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@Api(tags = "userResourceCategoryController", description = "用户资源分类管理")
@RequestMapping("/user/resource/category")
@RestController
public class UserResourceCategoryController {
    @Resource
    private UserResourceCategoryService userResourceCategoryService;
    @ApiOperation("分页获取资源分类列表")
    @PostMapping("/pageResourceCategory")
    public Result<PageResult<UserResourceCategory>> pageResourceCategory(PageCondition condition, UserResourceCategory userResourceCategory){
        if(condition == null || condition.getPageNum() == null || condition.getPageSize() == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<UserResourceCategory> query = new QueryWrapper<>(userResourceCategory);
        query.lambda().orderByDesc(UserResourceCategory::getCreateTime);
        Page<UserResourceCategory> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        userResourceCategoryService.page(page, query);
        if(page.getRecords() == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        PageResult<UserResourceCategory> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        pageResult.setData(page.getRecords());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, pageResult);
    }
    @ApiOperation("添加资源分类")
    @PostMapping("/addResourceCategory")
    public Result addResourceCategory(UserResourceCategory userResourceCategory){
        if(userResourceCategory == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        userResourceCategory.setCreateTime(DateUtils.getLocalCurrentTime())
                            .setSort(0);
        boolean save = userResourceCategoryService.save(userResourceCategory);
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
    @ApiOperation("修改资源分类")
    @PostMapping("/editResourceCategory")
    public Result editResourceCategory(UserResourceCategory userResourceCategory){
        if(userResourceCategory == null || userResourceCategory.getId() == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean save = userResourceCategoryService.updateById(userResourceCategory);
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
    @ApiOperation("批量删除资源分类")
    @PostMapping("/deleteResourceCategory")
    public Result deleteResourceCategory(@RequestParam List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean save = userResourceCategoryService.removeByIds(ids);
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
