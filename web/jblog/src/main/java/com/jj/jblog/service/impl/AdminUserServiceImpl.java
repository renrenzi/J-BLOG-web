package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.AdminUserMapper;
import com.jj.jblog.entity.AdminUser;
import com.jj.jblog.service.AdminUserService;
import org.springframework.stereotype.Service;

/**
 * @author 张俊杰
 * @date 2021/11/20  - {TIME}
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {
}
