package com.jj.jblog.controller.admin;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.DeleteStatusEnum;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.utils.DateUtils;
import com.jj.jblog.utils.IpAdrressUtil;
import com.jj.jblog.utils.MD5Utils;
import com.jj.jblog.entity.AdminImg;
import com.jj.jblog.entity.AdminUser;
import com.jj.jblog.entity.BlogCategory;
import com.jj.jblog.entity.LoginLog;
import com.jj.jblog.service.*;
import com.jj.jblog.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 *
 * @author 任人子
 * @date 2021/11/20  - {TIME}
 */
@RequestMapping("/admin")
@Api(tags = "AdminUserController", description = "后台用户管理")
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

    @PostMapping("/deleteUsers")
    @ApiOperation(value = "批量删除用户")
    public Result deleteUsers(@RequestParam List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            return  ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if(!adminUserService.removeByIds(ids)){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @PostMapping("/pageUser")
    @ApiOperation("分页所有用户")
    public Result<PageResult> pageUser(PageCondition<BlogCategory> condition, AdminUser user){
        if(StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if("".equals(user.getLoginUserName())){
            user.setLoginUserName(null);
        }
        return adminUserService.pageUser(condition, user);
    }
    @PostMapping("/register")
    @ApiOperation("用户注册")
    @Transactional(rollbackFor = {Exception.class})
    public Result<String> register(AdminUser user){
        if(user == null){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean isSaved;
        user.setLocked(0).setLoginPassword(MD5Utils.getMD5(user.getLoginPassword()));
        isSaved = adminUserService.save(user);
        isSaved = adminImgService.save(new AdminImg().setAdminId(user.getAdminUserId())
                                           .setUpdateTime(null)
                                           .setCreateTime(DateUtils.getLocalCurrentTime())
                                           .setIsDeleted(0)
                                           .setAdminImgUrl(StringConstants.DEFAULT_IMG_URL));
        if(!isSaved){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_GATEWAY);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "管理员登录校验")
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
        result.put("tokenName", StpUtil.getTokenName());
        if (adminImg != null) {
            result.put("imgUrl", adminImg.getAdminImgUrl());
        }
        insertLoginInfo(adminUser.getLoginUserName());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    /**
     * 插入登录信息
     *
     * @param loginName
     */
    private void insertLoginInfo(String loginName) {
        LoginLog log = new LoginLog().setLoginIp(IpAdrressUtil.getIpAdrress(request))
                .setLoginTime(DateUtils.getLocalCurrentTime())
                .setLoginName(loginName);
        loginLogService.save(log);
    }

    @GetMapping("/logOut")
    @ApiOperation(value = "登出")
    public Result logOut(Integer adminUserId) {
        StpUtil.logout(adminUserId);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, "登出成功");
    }

    @GetMapping("/count")
    @ApiOperation(value = "页面数据展示")
    public Result<Map<String, Integer>> getCount() {
        Map<String, Integer> result = new HashMap<String, Integer>(5);
        result.put("info", blogInfoService.count());
        result.put("category", blogCategoryService.count());
        result.put("comment", blogCommentService.count());
        result.put("tag", blogTagService.count());
        result.put("link", blogLinkService.count());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    @PostMapping("/uploadAuthorImg")
    @ApiOperation(value = "上传头像")
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

    @PostMapping("/editUserInfo")
    @ApiOperation(value = "修改用户信息")
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
