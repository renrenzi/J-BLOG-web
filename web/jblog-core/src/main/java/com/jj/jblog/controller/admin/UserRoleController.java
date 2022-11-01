package com.jj.jblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.RoleResourceRelation;
import com.jj.jblog.entity.UserRole;
import com.jj.jblog.pojo.dto.UserRoleRequestDto;
import com.jj.jblog.service.RoleResourceRelationService;
import com.jj.jblog.service.UserResourceService;
import com.jj.jblog.service.UserRoleService;
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
 * 用户角色管理Controller
 *
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@Api(tags = "userRoleController", description = "用户角色管理")
@RequestMapping("/user/role")
@RestController
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserResourceService userResourceService;
    @Resource
    private RoleResourceRelationService roleResourceRelationService;

    @ApiOperation("分页获取角色列表")
    @PostMapping("/pageRole")
    public Result<PageResult<UserRole>> pageRole(PageCondition condition, UserRole userRole) {
        if (condition == null || condition.getPageNum() == null || condition.getPageSize() == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<UserRole> query = new QueryWrapper<>(userRole);
        query.lambda().orderByDesc(UserRole::getCreateTime);
        Page<UserRole> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        userRoleService.page(page, query);
        if (page.getRecords() == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        PageResult<UserRole> pageResult = new PageResult<>();
        pageResult.setTotalSize(page.getTotal());
        pageResult.setData(page.getRecords());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, pageResult);
    }

    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Result addRole(UserRoleRequestDto userRoleRequestDto) {
        if (userRoleRequestDto == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        userRoleRequestDto.setCreateTime(DateUtils.getLocalCurrentTime())
                .setRoleStatus(1);
        userRoleService.save(userRoleRequestDto);
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation("添加角色并赋予对应资源")
    @PostMapping("/addRoleAndResource")
    @Transactional(rollbackFor = {Exception.class})
    public Result addRoleAndResource(UserRoleRequestDto userRoleRequestDto) {
        if (userRoleRequestDto == null || CollectionUtils.isEmpty(userRoleRequestDto.getResourceIds())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        userRoleRequestDto.setCreateTime(DateUtils.getLocalCurrentTime())
                .setRoleStatus(1);

        if (!userRoleService.save(userRoleRequestDto)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        saveRoleResourceRelationList(userRoleRequestDto);
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * 保存角色资源关系List
     * @param userRoleRequestDto
     * @return
     */
    private void saveRoleResourceRelationList(UserRoleRequestDto userRoleRequestDto){
        List<RoleResourceRelation> roleResourceRelationList = userRoleRequestDto.getResourceIds().stream()
                .map(id -> new RoleResourceRelation().setResourceId(id)
                        .setRoleId(userRoleRequestDto.getRoleId())).collect(Collectors.toList());
        roleResourceRelationService.deleteByList(roleResourceRelationList);
        roleResourceRelationService.saveBatch(roleResourceRelationList);
    }
    @ApiOperation("修改角色权限")
    @PostMapping("/editRole")
    @Transactional(rollbackFor = {Exception.class})
    public Result editRole(UserRoleRequestDto userRoleRequestDto) {
        if (userRoleRequestDto == null || userRoleRequestDto.getRoleId() == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        userRoleRequestDto.setCreateTime(DateUtils.getLocalCurrentTime());
        boolean save = userRoleService.updateById(userRoleRequestDto);
        if(!CollectionUtils.isEmpty(userRoleRequestDto.getResourceIds())){
            saveRoleResourceRelationList(userRoleRequestDto);
        }
        if(!save){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation("批量删除角色")
    @PostMapping("/deleteRoles")
    public Result deleteRoles(@RequestParam List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean save = userRoleService.removeByIds(ids);
        roleResourceRelationService.remove(new QueryWrapper<RoleResourceRelation>().lambda().eq(RoleResourceRelation::getRoleId, ids.get(0)));
        if (!save) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        userResourceService.initRoleResourceMap();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

}
