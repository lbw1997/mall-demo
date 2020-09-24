package com.abkm.mall.demo.config;

import com.abkm.mall.demo.common.config.BaseSwaggerConfig;
import com.abkm.mall.demo.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.abkm.mall.demo.module.ums")
                .title("mall-tiny项目骨架")
                .description("mall-tiny项目骨架相关接口文档")
                .contactName("abkm")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
