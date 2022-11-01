package com.jj.jblog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.RoleResourceRelation;
import com.jj.jblog.entity.UserResource;
import com.jj.jblog.service.RoleResourceRelationService;
import com.jj.jblog.service.UserResourceService;
import com.jj.jblog.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户资源管理Controller
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@Api(tags = "userResourceController", description = "用户资源管理")
@RequestMapping("/user/resource")
@RestController
public class UserResourceController {

    @Resource
    private UserResourceService userResourceService;
    @Resource
    private RoleResourceRelationService roleResourceRelationService;
    @ApiOperation("获取角色对应资源")
    @PostMapping("/getResourceByRoleId")
    @Transactional(rollbackFor = {Exception.class})
    public Result<List<UserResource>> getResourceByRoleId(Integer roleId){
        if(roleId == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        List<Integer> resourceIds = roleResourceRelationService.list(new QueryWrapper<RoleResourceRelation>()
                .lambda()
                .eq(RoleResourceRelation::getRoleId, roleId))
                .stream()
                .map(RoleResourceRelation::getResourceId)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(resourceIds)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        List<UserResource> resourceList = userResourceService.listByIds(resourceIds);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, resourceList);
    }
    @ApiOperation("分页获取资源列表")
    @PostMapping("/pageResource")
    public Result<PageResult<UserResource>> pageResource(PageCondition condition, UserResource userResource){
        if(condition == null || condition.getPageNum() == null || condition.getPageSize() == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<UserResource> query = new QueryWrapper<>(userResource);
        query.lambda().orderByDesc(UserResource::getCreateTime);
        Page<UserResource> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        userResourceService.page(page, query);
        if(page.getRecords() == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        PageResult<UserResource> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        pageResult.setData(page.getRecords());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, pageResult);
    }
    @ApiOperation("添加资源")
    @PostMapping("/addResource")
    public Result addResource(UserResource userResource){
        if(userResource == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        userResource.setCreateTime(DateUtils.getLocalCurrentTime());
        boolean save = userResourceService.save(userResource);
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * @param userResource
     * @return
     */
    @ApiOperation("修改资源")
    @PostMapping("/editResource")
    public Result editResource(UserResource userResource){
        if(userResource == null || userResource.getId() == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        // 前端传参问题
        userResource.setCreateTime(null);
        boolean save = userResourceService.updateById(userResource);
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
    @ApiOperation("批量删除资源")
    @PostMapping("/deleteResources")
    public Result deleteResources(@RequestParam List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean save = userResourceService.removeByIds(ids);
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

}
