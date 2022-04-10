package com.jj.jblog.controller.blog;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.*;
import com.jj.jblog.entity.AdminImg;
import com.jj.jblog.entity.AdminUser;
import com.jj.jblog.entity.LoginLog;
import com.jj.jblog.service.*;
import com.jj.jblog.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户管理
 * @author 张俊杰
 * @date 2021/11/20  - {TIME}
 */
@Api(tags = "AdminUserController", description = "后台用户管理")
@RequestMapping("admin")
@RestController
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogCategoryService blogCategoryService;
    @Resource
    private BlogTagService blogTagService;
    @Resource
    private BlogCommentService blogCommentService;
    @Resource
    private BlogLinkService blogLinkService;
    @Resource
    private AdminImgService adminImgService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private LonginLogService loginLogService;

    @ApiOperation(value = "管理员登录校验")
    @PostMapping("/login")
    public Result<Map<String, Object>> adminLogin(AdminUser adminUser) {
        String password = adminUser.getLoginPassword();
        if (StringUtils.isEmpty(adminUser.getLoginUserName()) || StringUtils.isEmpty(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> query = new QueryWrapper<AdminUser>(adminUser.setLoginPassword(MD5Utils.getMD5(password)));
        AdminUser exits = adminUserService.getOne(query);
        if (exits == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED);
        }
        StpUtil.login(exits.getAdminUserId());
        AdminImg adminImg = adminImgService.getOne(new QueryWrapper<AdminImg>().lambda()
                .eq(AdminImg::getAdminId, exits.getAdminUserId()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("adminUser", exits);
        // SA-token 返回token值
        result.put("token", StpUtil.getTokenValue());
        if (adminImg != null) {
            result.put("imgUrl", adminImg.getAdminImgUrl());
        }
        insertLoginInfo(adminUser.getLoginUserName());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    /**
     * 插入登录信息
     * @param loginName 
     */
    private void insertLoginInfo(String loginName){
        LoginLog log = new LoginLog().setLoginIp(IpAdrressUtil.getIpAdrress(request))
                                    .setLoginTime(DateUtils.getLocalCurrentTime())
                                    .setLoginName(loginName);
        loginLogService.save(log);
    }
    
    @ApiOperation(value = "登出")
    @GetMapping("/logOut")
    public Result logOut() {
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation(value = "页面数据展示")
    @GetMapping("/count")
    public Result<Map<String, Integer>> getCount() {
        Map<String, Integer> result = new HashMap<String, Integer>(5);
        result.put("info", blogInfoService.count());
        result.put("category", blogCategoryService.count());
        result.put("comment", blogCommentService.count());
        result.put("tag", blogTagService.count());
        result.put("link", blogLinkService.count());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    @ApiOperation(value = "上传头像")
    @PostMapping("/uploadAuthorImg")
    @Transactional(rollbackFor = {Exception.class})
    public Result<String> uploadAuthorImg(MultipartFile userImage, AdminUser adminUser) {

        if (ObjectUtils.isEmpty(userImage) || ObjectUtils.isEmpty(adminUser)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean flag;
        String url = UploadFileUtil.getUploadFileUrl(userImage);
        // 将图片路径保存到数据库中
        Integer adminId = adminUser.getAdminUserId();
        AdminImg adminImg = new AdminImg().setAdminId(adminId)
                .setAdminImgUrl(url)
                .setUpdateTime(DateUtils.getLocalCurrentTime());
        QueryWrapper<AdminImg> query = new QueryWrapper<AdminImg>();
        query.lambda().eq(AdminImg::getAdminId, adminId);
        if (ObjectUtils.isEmpty(adminImgService.getOne(query))) {
            flag = adminImgService.save(adminImg.setIsDeleted(DeleteStatusEnum.NOT_DELETED.getStatus())
                    .setCreateTime(DateUtils.getLocalCurrentTime()));
        } else {
            flag = adminImgService.update(adminImg, new QueryWrapper<AdminImg>().lambda()
                    .eq(AdminImg::getAdminId, adminId));
        }
        if (!flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, url);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/editUserInfo")
    public Result<String> editUserInfo(AdminUser adminUser) {
        if (ObjectUtils.isEmpty(adminUser)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        String password = adminUser.getLoginPassword();
        if (!StringUtils.isEmpty(password)) {
            adminUser.setLoginPassword(MD5Utils.getMD5(password));
        } else {
            adminUser.setLoginPassword(null);
        }
        if (!adminUserService.updateById(adminUser)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }


}
