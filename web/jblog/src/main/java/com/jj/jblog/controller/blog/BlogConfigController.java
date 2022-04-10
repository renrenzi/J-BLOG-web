package com.jj.jblog.controller.blog;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.BlogConfig;
import com.jj.jblog.service.BlogConfigService;
import com.jj.jblog.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 博客系统配置管理
 * @author 张俊杰
 * @date 2021/11/18  - {TIME}
 */
@Api(tags = "blogConfigController", description = "博客系统配置管理")
@RestController
@RequestMapping("config")
public class BlogConfigController {
    @Resource
    private BlogConfigService blogConfigService;


    @ApiOperation("获取系统配置列表")
    @GetMapping("/getConfigList")
    public Result<List<BlogConfig>> getConfigList() {
        List<BlogConfig> blogConfigList = blogConfigService.list();
        if (CollectionUtils.isEmpty(blogConfigList)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,blogConfigList);
    }

    @ApiOperation("添加系统配置")
    @PostMapping("/addConfig")
    public Result<String> addConfig(BlogConfig blogConfig) {
        if (ObjectUtils.isEmpty(blogConfig)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (!blogConfigService.save(blogConfig.setCreateTime(DateUtils.getLocalCurrentTime())
                .setUpdateTime(DateUtils.getLocalCurrentTime()))) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation("更新系统配置")
    @PostMapping("/updateConfig")
    public Result<String> updateConfig(BlogConfig blogConfig) {
        if (ObjectUtils.isEmpty(blogConfig)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (!blogConfigService.updateById(blogConfig.setUpdateTime(DateUtils.getLocalCurrentTime()))) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    @ApiOperation("删除系统配置")
    @PostMapping("/deleteConfig")
    public Result<String> deleteConfig(String configField){
        if (StringUtils.isEmpty(configField)){
            ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (!blogConfigService.removeById(configField)){
            ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
