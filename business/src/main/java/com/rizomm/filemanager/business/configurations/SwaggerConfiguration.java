package com.rizomm.filemanager.business.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket buildApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("FIle manager")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rizomm.filemanager.server.controllers"))
                .paths(PathSelectors.regex("/files.*")).build();
    }
}
