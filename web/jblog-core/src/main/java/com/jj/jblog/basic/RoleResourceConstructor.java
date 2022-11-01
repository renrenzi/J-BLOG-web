package com.jj.jblog.basic;

import com.jj.jblog.service.UserResourceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 初始化角色资源Map
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
@Component
public class RoleResourceConstructor {

    @Resource
    private UserResourceService userResourceService;

    @PostConstruct
    public void initRoleResourceMap(){
        userResourceService.initRoleResourceMap();
    }
}
