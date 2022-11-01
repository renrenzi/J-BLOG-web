package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.dao.AdminImgMapper;
import com.jj.jblog.dao.AdminUserMapper;
import com.jj.jblog.entity.AdminImg;
import com.jj.jblog.entity.AdminUser;
import com.jj.jblog.entity.BlogCategory;
import com.jj.jblog.pojo.dto.UserInfoDto;
import com.jj.jblog.service.AdminUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户ServiceImpl
 * @author 任人子
 * @date 2021/11/20  - {TIME}
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private AdminImgMapper imgMapper;

    @Override
    public Result<PageResult> pageUser(PageCondition<BlogCategory> condition, AdminUser user) {
        QueryWrapper<AdminUser> query = new QueryWrapper<>(user);
        query.lambda().orderByDesc(AdminUser::getAdminUserId);
        Page page = new Page(condition.getPageNum(), condition.getPageSize());
        userMapper.selectPage(page, query);

        if (CollectionUtils.isEmpty(page.getRecords())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        List<AdminUser> adminUsers = page.getRecords();
        List<UserInfoDto> res = new ArrayList<>();
        copyAdminUserList(adminUsers, res);

        PageResult<UserInfoDto> result = new PageResult<>();
        result.setTotalSize(page.getTotal());
        result.setData(res);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    /**
     * 拷贝并封装用户列表
     * @param adminUsers
     * @param res
     */
    private void copyAdminUserList(List<AdminUser> adminUsers, List<UserInfoDto> res){
        adminUsers.forEach(u ->{
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(u, userInfoDto);
            AdminImg adminImg = new AdminImg();
            adminImg.setAdminId(u.getAdminUserId());
            userInfoDto.setImgUrl(imgMapper.selectOne(new QueryWrapper<>(adminImg))
                    .getAdminImgUrl());
            res.add(userInfoDto);
        });
    }
}
