package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.dao.RoleResourceRelationMapper;
import com.jj.jblog.dao.UserResourceMapper;
import com.jj.jblog.dao.UserRoleMapper;
import com.jj.jblog.entity.RoleResourceRelation;
import com.jj.jblog.entity.UserResource;
import com.jj.jblog.entity.UserRole;
import com.jj.jblog.service.RedisService;
import com.jj.jblog.service.UserResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户资源ServiceImpl
 *
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@Service
public class UserResourceServiceImpl extends ServiceImpl<UserResourceMapper, UserResource> implements UserResourceService {

    @Resource
    private RedisService redisService;
    @Resource
    private UserResourceMapper userResourceMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleResourceRelationMapper roleResourceRelationMapper;

    @Override
    public Map<String, List<String>> initRoleResourceMap() {
        Map<String, List<String>> roleResourceMap = new HashMap<>();
        List<UserRole> roleList = userRoleMapper.selectList(new QueryWrapper<>());
        List<UserResource> resourceList = userResourceMapper.selectList(new QueryWrapper<>());
        List<RoleResourceRelation> roleRelationList = roleResourceRelationMapper.selectList(new QueryWrapper<>());
        for (UserResource userResource : resourceList) {
            Set<Integer> roleIds = roleRelationList.stream()
                    .filter(item -> item.getResourceId().equals(userResource.getId()))
                    .map(RoleResourceRelation::getRoleId)
                    .collect(Collectors.toSet());
            List<String> roleNames = roleList.stream()
                    .filter(item -> roleIds.contains(item.getRoleId()))
                    .map(item -> item.getRoleId() + "_" + item.getRoleName())
                    .collect(Collectors.toList());
            roleResourceMap.put(userResource.getUrl(), roleNames);
        }
        redisService.del(StringConstants.RESOURCE_ROLE_MAP_KEY);
        redisService.hSetAll(StringConstants.RESOURCE_ROLE_MAP_KEY, roleResourceMap);
        return roleResourceMap;
    }
}
