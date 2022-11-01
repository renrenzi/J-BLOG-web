package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.UserRoleMapper;
import com.jj.jblog.entity.UserRole;
import com.jj.jblog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色ServiceImpl
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
