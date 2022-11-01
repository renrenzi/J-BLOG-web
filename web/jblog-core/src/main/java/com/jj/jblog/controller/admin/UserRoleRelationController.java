package com.jj.jblog.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.UserRole;
import com.jj.jblog.entity.UserRoleRelation;
import com.jj.jblog.service.UserRoleRelationService;
import com.jj.jblog.service.UserRoleService;
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
 * 用户角色关系Controller
 *
 * @author 任人子
 * @date 2022/5/15  - {TIME}
 */
@Api(tags = "userRoleRelationController", description = "用户角色关系管理")
@RequestMapping("/user/role/relation")
@RestController
public class UserRoleRelationController {
    @Resource
    private UserRoleRelationService userRoleRelationService;
    @Resource
    private UserRoleService userRoleService;

    @ApiOperation("分配角色")
    @PostMapping("/allocateRole")
    @Transactional(rollbackFor = {Exception.class})
    public Result allocateRole(Integer adminId,@RequestParam List<Integer> roleIds) {
        if (adminId == null || CollectionUtils.isEmpty(roleIds)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        userRoleRelationService.remove(new QueryWrapper<UserRoleRelation>()
                .lambda().eq(UserRoleRelation::getAdminId, adminId));
        userRoleRelationService.saveBatch(roleIds.stream().map(item -> new UserRoleRelation().setAdminId(adminId)
                .setRoleId(item)).collect(Collectors.toList()));
        return  ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation("获取该用户所有角色")
    @PostMapping("/getRoleListById")
    public Result<List<UserRole>> getRoleListById(Integer adminId) {
        if (adminId == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        List<Integer> roleIds = userRoleRelationService.list(new QueryWrapper<UserRoleRelation>().lambda()
                .eq(UserRoleRelation::getAdminId, adminId))
                .stream()
                .map(UserRoleRelation::getRoleId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIds)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, null);
        }
        List<UserRole> roleList = userRoleService.listByIds(roleIds);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, roleList);
    }
}
