package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.entity.AdminUser;
import com.jj.jblog.entity.BlogCategory;

/**
 * 用户Service
 * @author 张俊杰
 * @date 2021/11/20  - {TIME}
 */
public interface AdminUserService extends IService<AdminUser> {
    /**
     * 分页查询用户列表
     * @param condition
     * @param user
     * @return
     */
    Result<PageResult> pageUser(PageCondition<BlogCategory> condition, AdminUser user);
}
