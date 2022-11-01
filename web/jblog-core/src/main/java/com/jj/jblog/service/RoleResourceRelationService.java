package com.jj.jblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jj.jblog.entity.RoleResourceRelation;

import java.util.List;

/**
 * 角色资源关系Service
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
public interface RoleResourceRelationService extends IService<RoleResourceRelation> {
    /**
     * 删除角色资源关系List
     * @param roleResourceRelationList
     * @return
     */
    int deleteByList(List<RoleResourceRelation> roleResourceRelationList);
}
