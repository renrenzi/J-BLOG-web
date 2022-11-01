package com.jj.jblog.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.dao.UserRoleMapper;
import com.jj.jblog.dao.UserRoleRelationMapper;
import com.jj.jblog.entity.*;
import com.jj.jblog.service.RedisService;
import com.jj.jblog.service.UserResourceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 *
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisService redisService;
    @Resource
    private UserResourceService userResourceService;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     *
     * @param adminUserId 登录账号Id
     * @param s
     * @return
     */
    @Override
    public List<String> getPermissionList(Object adminUserId, String s) {
        Map<Object, Object> roleResourceMap = redisService.hGetAll(StringConstants.RESOURCE_ROLE_MAP_KEY);
        List<String> permissions = new ArrayList<>();
        if(CollectionUtils.isEmpty(roleResourceMap)){
            return permissions;
        }
        List<Integer> roleIds = userRoleRelationMapper.selectList(new QueryWrapper<UserRoleRelation>()
                .lambda()
                .eq(UserRoleRelation::getAdminId, adminUserId))
                .stream()
                .map(UserRoleRelation::getRoleId)
                .collect(Collectors.toList());
        for (Map.Entry<Object, Object> entry : roleResourceMap.entrySet()) {
            String key = (String) entry.getKey();
            int contains = (int) ((List<String>) (entry.getValue())).stream()
                    .map(item -> Integer.valueOf(item.split("_")[0])).filter(roleIds::contains).count();
            if (contains > 0) {
                permissions.add(key.split("/")[1]);
            }
        }
        return permissions;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     *
     * @param adminUserId
     * @param s
     * @return
     */
    @Override
    public List<String> getRoleList(Object adminUserId, String s) {
        List<Integer> roleIds = userRoleRelationMapper.selectList(new QueryWrapper<UserRoleRelation>()
                .lambda()
                .eq(UserRoleRelation::getAdminId, adminUserId))
                .stream()
                .map(UserRoleRelation::getRoleId)
                .collect(Collectors.toList());

        return userRoleMapper.selectBatchIds(roleIds)
                .stream()
                .map(UserRole::getRoleName)
                .collect(Collectors.toList());
    }
}
