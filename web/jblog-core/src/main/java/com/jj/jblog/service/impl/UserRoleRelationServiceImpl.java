package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.UserRoleRelationMapper;
import com.jj.jblog.entity.UserRoleRelation;
import com.jj.jblog.service.UserRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关系ServiceImpl
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation> implements UserRoleRelationService {
}
