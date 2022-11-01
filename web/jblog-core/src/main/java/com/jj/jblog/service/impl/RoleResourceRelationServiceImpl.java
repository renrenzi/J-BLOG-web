package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.RoleResourceRelationMapper;
import com.jj.jblog.entity.RoleResourceRelation;
import com.jj.jblog.service.RoleResourceRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源角色关系ServiceImpl
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
@Service
public class RoleResourceRelationServiceImpl extends ServiceImpl<RoleResourceRelationMapper, RoleResourceRelation> implements RoleResourceRelationService {
    @Resource
    private RoleResourceRelationMapper relationMapper;
    @Override
    public int deleteByList(List<RoleResourceRelation> roleResourceRelationList) {
        return relationMapper.deleteByList(roleResourceRelationList);
    }
}
