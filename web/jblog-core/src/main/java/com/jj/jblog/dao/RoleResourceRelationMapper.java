package com.jj.jblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jj.jblog.entity.RoleResourceRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色资源关系Mapper
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
public interface RoleResourceRelationMapper extends BaseMapper<RoleResourceRelation> {
    /**
     * 删除角色资源关系List
     * @param roleResourceRelationList
     * @return
     */
    int deleteByList(@Param("roleResourceRelationList") List<RoleResourceRelation> roleResourceRelationList);
}
