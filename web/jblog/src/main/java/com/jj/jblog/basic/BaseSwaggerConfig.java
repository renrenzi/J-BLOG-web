package com.jj.jblog.basic;

import com.jj.jblog.pojo.dto.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 基本配置
 * @author 任人子
 * @date 2022/4/8  - {TIME}
 */
public abstract class BaseSwaggerConfig {

    @Bean
    public Docket createRestAPI(){
        SwaggerProperties swaggerProperties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo(swaggerProperties))
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                        .paths(PathSelectors.any())
                        .build();
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties){
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties().getContactEmail()))
                .build();
    }
    /**
     * 自定义Swagger配置
     * @return
     */
    public abstract SwaggerProperties swaggerProperties();
}
