package com.jj.jblog.controller.blog;

import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.entity.BlogConfig;
import com.jj.jblog.service.BlogConfigService;
import com.jj.jblog.util.DateUtils;
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
 * @author 张俊杰
 * @date 2021/11/18  - {TIME}
 */
@RestController
@RequestMapping("config")
public class BlogConfigController {
    @Resource
    private BlogConfigService blogConfigService;

    /**
     * 获取系统配置列表
     *
     * @return
     */
    @GetMapping("/getConfigList")
    public Result<List<BlogConfig>> getConfigList() {
        List<BlogConfig> blogConfigList = blogConfigService.list();
        if (CollectionUtils.isEmpty(blogConfigList)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,blogConfigList);
    }

    /**
     * 添加系统配置
     *
     * @param blogConfig
     * @return
     */
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

    /**
     * 更新系统配置
     *
     * @param blogConfig
     * @return
     */
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
