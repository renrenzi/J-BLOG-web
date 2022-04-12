package com.jj.jblog.config;

import com.jj.jblog.basic.BaseSwaggerConfig;
import com.jj.jblog.pojo.dto.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Config
 * @author 任人子
 * @date 2022/4/8  - {TIME}
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.jj.jblog.controller")
                .title("blog后台管理中心")
                .description("blog后台管理中心相干接口文档")
                .version("1.0")
                .contactName("renrenzi")
                .enableSecurity(false)
                .build();
    }
}
