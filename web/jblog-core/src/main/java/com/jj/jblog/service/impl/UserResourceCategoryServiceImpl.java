package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.UserResourceCategoryMapper;
import com.jj.jblog.entity.UserResourceCategory;
import com.jj.jblog.service.UserResourceCategoryService;
import org.springframework.stereotype.Service;

/**
 * 用户资源分类管理ServiceImpl
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@Service
public class UserResourceCategoryServiceImpl extends ServiceImpl<UserResourceCategoryMapper, UserResourceCategory> implements UserResourceCategoryService {
}
