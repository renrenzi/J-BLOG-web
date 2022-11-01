package com.jj.jblog.Interceptor;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.service.RedisService;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一认证路由拦截器
 *
 * @author 任人子
 * @date 2022/3/26  - {TIME}
 */
@Configuration
public class RouterInterceptor implements WebMvcConfigurer {
    @Resource
    private RedisService redisService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            Map<Object, Object> roleResourceMap = redisService.hGetAll(StringConstants.RESOURCE_ROLE_MAP_KEY);
            if (!CollectionUtils.isEmpty(roleResourceMap)) {
                for (Map.Entry<Object, Object> entry : roleResourceMap.entrySet()) {
                    String url = (String) entry.getKey();
                    String[] roleList = ((List<String>) (entry.getValue())).stream().map(
                            item -> item.split("_")[1]).collect(Collectors.toList()).toArray(new String[0]);
                    SaRouter.match(url, r -> StpUtil.checkRoleOr(roleList));
                }
            }
        })).addPathPatterns("/**")
                .excludePathPatterns("/blog/comment/createComment")
                .excludePathPatterns("/blog/tag/getBlogTag")
                .excludePathPatterns("/blog/link/pageLinkList")
                .excludePathPatterns("/front/view/**")
                .excludePathPatterns("/admin/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
