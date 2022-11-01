package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.entity.UserResource;

import java.util.List;
import java.util.Map;

/**
 * 用户资源Service
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
public interface UserResourceService extends IService<UserResource> {

    /**
     * 初始化资源角色关系Map并保存到Redis中
     * @return
     */
    Map<String, List<String>> initRoleResourceMap();
}
